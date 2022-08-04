package com.petswote.pet.edit

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.parseDateString
import com.petsvote.dialog.InformationKindDialog
import com.petsvote.dialog.LoginInstaDialog
import com.petsvote.dialog.SelectPhotoDialog
import com.petsvote.dialog.UserImageDialog
import com.petsvote.domain.entity.pet.Pet
import com.petsvote.domain.entity.pet.PetPhoto
import com.petsvote.navigation.MainNavigation
import com.petsvote.ui.ext.disabled
import com.petsvote.ui.ext.enabled
import com.petsvote.ui.ext.hide
import com.petsvote.ui.ext.show
import com.petsvote.ui.maintabs.BesieTabLayoutSelectedListener
import com.petsvote.ui.maintabs.BesieTabSelected
import com.petswote.pet.R
import com.petswote.pet.add.AddPetFragment
import com.petswote.pet.add.AddPetPhotoAdapter
import com.petswote.pet.add.AddPetViewModel
import com.petswote.pet.crop.CropPetActivity
import com.petswote.pet.databinding.FragmentAddPetBinding
import com.petswote.pet.di.PetComponentViewModel
import com.petswote.pet.helpers.OnStartDragListener
import com.petswote.pet.helpers.SimpleItemTouchHelperCallback
import com.petswote.pet.info.PetInfoFragment
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.vponomarenko.injectionmanager.x.XInjectionManager
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

open class EditPetFragment: BaseFragment(R.layout.fragment_add_pet), OnStartDragListener,
    SelectPhotoDialog.SelectPhotoDialogListener, BesieTabLayoutSelectedListener,
    LoginInstaDialog.LoginInstaDialogListener {

    private var mItemTouchHelper: ItemTouchHelper? = null
    private var listPhotos = mutableListOf<PetPhoto>()
    val adapter = AddPetPhotoAdapter(activity, this)

    private var binding: FragmentAddPetBinding? = null

    private var isAddPhotoDialog = true;
    private val dialogAddPhoto = UserImageDialog()
    private val selectPhotoDialog = SelectPhotoDialog(1)
    var informationDialog = InformationKindDialog()

    @Inject
    internal lateinit var viewModelFactory: Lazy<EditPetViewModel.Factory>

    private val petComponentViewModel: PetComponentViewModel by viewModels()
    val viewModel: EditPetViewModel by viewModels {
        viewModelFactory.get()
    }

    val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    var isBreedClick = false

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent?.getBooleanExtra(
                        SelectPhotoDialog.EXTRA_MESSAGE_VALUE,
                        false
                    ) == true
                ) selectPhotoDialog.dismiss()
            }
        }

    var cal = Calendar.getInstance()
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    val myFormat = "dd.MM.yyyy"
    val serverFormat = "yyyy-MM-dd HH:mm:ss ZZZ"
    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
    val sdfServer = SimpleDateFormat(serverFormat, Locale.getDefault())

    var validateCreatePet = ValidateCreatePet()

    companion object {
        const val EXTRA_MESSAGE = "CROP_MESSAGE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddPetBinding.bind(view)

        initRecycler()
        initDialog()
        initTW()
        requestPermissions()

        binding?.title?.text = getString(com.petsvote.ui.R.string.pet_edit)

        var pet: Pet? = Json.decodeFromString(activity?.intent?.extras?.getString("pet") ?: "") as Pet
        pet?.let {
            binding?.username?.setText(it.name, TextView.BufferType.EDITABLE)

            binding?.birthday?.text = parseDateString(it.bdate)
            binding?.birthday?.alpha = 1f
            binding?.birthday?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.active_text_color))
            validateCreatePet.birthday = pet.bdate
            validateCreatePet.name = pet.name

            when (pet.type) {
                "reptile", "amphibian", "invertebrates" -> {
                    binding?.containerTabs2?.visibility = View.VISIBLE
                    binding?.containerTabs?.visibility = View.GONE

                    if(pet.sex == "MALE") binding?.tabs2?.initCountryTabs()
                    else if(pet.sex == "FEMALE") {}
                    else binding?.tabs2?.initWorldTab()
                }
                else -> {
                    binding?.containerTabs2?.visibility = View.GONE
                    binding?.containerTabs?.visibility = View.VISIBLE

                    if(pet.sex == "MALE") binding?.tabs?.initCountryTabs()
                }
            }


            for (i in 0 until pet.photos.size)
                adapter.addItem(PetPhoto(id = i, image = pet.photos[i].url))

            viewModel.setNamePet(pet.name)
            viewModel.setBirthdayPet(pet.bdate)
            viewModel.setSexPet(if (pet.sex == "FEMALE") 0 else 1)

            if(pet.inst.isNotEmpty()) stateInstagramEnabled(it.inst)

            var kindTitle = activity?.intent?.extras?.getString("petKind")
            //binding?.kids?.text = kindTitle

            lifecycleScope.launchWhenStarted {
                viewModel.getKindByName(pet.type)
                viewModel.setBreedsByIdName(pet.breed_id)
            }

            checkBTN()
        }



        lifecycleScope.launchWhenResumed { viewModel.getConfiguration() }
    }

    private fun initTW() {
        binding?.username?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.setNamePet(p0.toString())
                validateCreatePet.name = p0.toString()
                checkBTN()
            }

        })

        binding?.containerKids?.setOnClickListener {
            activity?.let { it1 -> navigation.startSelectKindsFromEditPet(it1) }

        }

        binding?.containerBreeds?.setOnClickListener {
            if(isBreedClick){
                activity?.let { it1 -> navigation.startSelectBreedsFromEditPet(it1) }
            }
            else if (!informationDialog.isAdded)
                informationDialog.show(childFragmentManager, "informationDialog")
        }

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                viewModel.setBirthdayPet(sdfServer.format(cal.time))
                validateCreatePet.birthday = sdfServer.format(cal.time)
                binding?.birthday?.text = sdf.format(cal.time)
                binding?.birthday?.alpha = 1f
                binding?.birthday?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.active_text_color))
                checkBTN()
            }

        binding?.containerBirthday?.setOnClickListener {
            //it.isPressed = true
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding?.tabs?.setTabSelectedListener(this)
        binding?.tabs2?.setTabSelectedListener(this)

        binding?.insta?.root?.setOnClickListener {
            if (validateCreatePet.inst.isEmpty()) {
                var dialogLogin = LoginInstaDialog()
                dialogLogin.setLoginInstaDialogListener(this)
                dialogLogin.show(childFragmentManager, "LoginInstaDialog")
            }
        }

        binding?.insta?.gpInstaDisconnect?.setOnClickListener {
            validateCreatePet.inst = ""
            stateInstagramDisabled()
        }

        binding?.close?.setOnClickListener {
            activity?.setResult(Activity.RESULT_OK)
            activity?.finish()
        }


        binding?.save?.setOnClickListener {
            viewModel.editPet(emptyList())
        }
    }

    fun checkBTN() {
        if(
            adapter.items.filter { it.bitmap != null || it.image != null}.isNotEmpty()
            && validateCreatePet.name.isNotEmpty()
            && validateCreatePet.breed.isNotEmpty()
            && validateCreatePet.kind.isNotEmpty()
            && validateCreatePet.birthday.isNotEmpty()
        ){
            binding?.save?.enabled()
            binding?.saveText?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.besie_tab_text_selected_color))
        }else {
            binding?.save?.disabled()
            binding?.saveText?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.besie_tab_text_unselected_color))
        }
    }

    private fun initDialog() {
        dialogAddPhoto.setUserLocationDialogListener(object :
            UserImageDialog.UserImageDialogListener {
            override fun next() {
                viewModel.setAddPhotoDialog()
                isAddPhotoDialog = false
                showDialogImage()
            }

        })
    }

    private fun initRecycler() {

        val recyclerView: RecyclerView? = binding?.photosPetList
        val layoutManager = GridLayoutManager(activity, 3)
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)

        if (listPhotos.isEmpty()) {
            for (i in 0..5) {
                listPhotos.add(PetPhoto(null))
            }
            adapter.addData(listPhotos)
        }

        recyclerView?.setHasFixedSize(true)
        recyclerView?.setAdapter(adapter)
        recyclerView?.setLayoutManager(layoutManager)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper?.attachToRecyclerView(recyclerView)
    }


    private fun showDialogImage() {

        selectPhotoDialog.setSelectPhotoDialogListener(this)

        if (isAddPhotoDialog) {
            if (!dialogAddPhoto.isAdded)
                dialogAddPhoto.show(childFragmentManager, "UserImageDialog")
        } else {
            if (checkCameraPermissions() || checkReadPermissions()) {
                if (!selectPhotoDialog.isAdded)
                    selectPhotoDialog.show(childFragmentManager, "SelectPhotoDialog")
            }
        }
    }


    override fun initObservers() {


        lifecycleScope.launchWhenStarted {
            viewModel.kindId.collect {
                it.let { id ->
                    when (id) {
                        4, 6, 9 -> {
                            binding?.containerTabs2?.visibility = View.VISIBLE
                            binding?.containerTabs?.visibility = View.GONE
                        }
                        else -> {
                            binding?.containerTabs2?.visibility = View.GONE
                            binding?.containerTabs?.visibility = View.VISIBLE
                        }
                    }

                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.listPhotosPet.collect {list ->
                for (i in list)
                    adapter.addItem(i)
                checkBTN()
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.kindTitle.collect {
                if(it.isNotEmpty()) {
                    isBreedClick = true
                    binding?.rightBreeds?.visibility = View.VISIBLE
                    binding?.kids?.text = it
                    validateCreatePet.kind = it
                    binding?.kids?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.active_text_color))
                }else {
                    isBreedClick = false
                    binding?.rightBreeds?.visibility = View.GONE
                    binding?.kids?.text = getString(com.petsvote.ui.R.string.kinds_lower)
                    validateCreatePet.kind = ""
                    binding?.kids?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.hint_text_color))
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.breedTitle.collect {
                if(it.isNotEmpty()) {
                    binding?.breeds?.text = it
                    binding?.breeds?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.active_text_color))
                }else {
                    binding?.breeds?.text = getString(com.petsvote.ui.R.string.breed)
                    binding?.breeds?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.hint_text_color))
                }
                validateCreatePet.breed = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collect {
                if(it){
                    binding?.save?.enabled()
                    binding?.saveText?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.besie_tab_text_selected_color))
                }else {
                    binding?.save?.disabled()
                    binding?.saveText?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.besie_tab_text_unselected_color))
                }
                stateInstagramLoading(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.instagramUserName.collect {
                if (it.isNotEmpty()) stateInstagramEnabled(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addPet.collect {pet ->
                pet?.let {
                    activity?.setResult(Activity.RESULT_OK)
                    activity?.finish()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.getKindTitle()
            viewModel.getBreedTitle()
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {

    }

    override fun onClick() {
        showDialogImage()
    }

    override fun onClose(petPhoto: PetPhoto) {
        viewModel.removeItem(petPhoto.id ?: -1)
    }

    override fun crop() {
        startForResult.launch(Intent(activity, CropPetActivity::class.java))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        petComponentViewModel.petComponent.injectEdit(this)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                activity?.setResult(Activity.RESULT_OK)
                activity?.finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    override fun selected(tab: BesieTabSelected) {
        var sex  = when (tab) {
            BesieTabSelected.ALL -> 0
            BesieTabSelected.MAN -> 1
            BesieTabSelected.GIRLS -> 2
            else -> 0
        }
        viewModel.setSexPet(sex)
    }

    data class ValidateCreatePet(
        var countPhotos: Int = 0,
        var name: String = "",
        var breed: String = "",
        var kind: String = "",
        var birthday: String = "",
        var inst: String =""
    )

    override fun setUsername(userId: Long?) {
        if (userId != null) viewModel.getUserName(userId)
    }

    fun stateInstagramEnabled(usernameInstagram: String) {
        validateCreatePet.inst = usernameInstagram
        binding?.insta?.gpInstaUsernameContainer?.show()
        binding?.insta?.gpInstaUsername?.text = usernameInstagram
        binding?.insta?.connectInstagram?.hide()
        binding?.insta?.gpInstaProgress?.hide()
    }

    fun stateInstagramLoading(it: Boolean) {

        binding?.insta?.gpInstaProgress?.visibility = if (it) View.VISIBLE else View.GONE
        if (it) binding?.insta?.connectInstagram?.visibility = View.GONE
    }

    private fun stateInstagramDisabled() {
        binding?.insta?.gpInstaUsernameContainer?.hide()
        binding?.insta?.connectInstagram?.show()
        binding?.insta?.gpInstaProgress?.hide()
    }

}
//class EditPetFragment: BaseFragment() {
//
//   private var binding: FragmentAddPetBinding? = null
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding = FragmentAddPetBinding.bind(view)
//
//        binding?.title?.text = getString(com.petsvote.ui.R.string.pet_edit)
//
//        var pet: Pet? = Json.decodeFromString(activity?.intent?.extras?.getString("pet") ?: "") as Pet
//        pet?.let {
//            binding?.username?.setText(it.name, TextView.BufferType.EDITABLE)
//
//            binding?.birthday?.text = parseDateString(it.bdate)
//            binding?.birthday?.alpha = 1f
//            binding?.birthday?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.active_text_color))
//            validateCreatePet.birthday = pet.bdate
//            validateCreatePet.name = pet.name
//
//            when (pet.type) {
//                "reptile", "amphibian", "invertebrates" -> {
//                    binding?.containerTabs2?.visibility = View.VISIBLE
//                    binding?.containerTabs?.visibility = View.GONE
//                }
//                else -> {
//                    binding?.containerTabs2?.visibility = View.GONE
//                    binding?.containerTabs?.visibility = View.VISIBLE
//                }
//            }
//
//            for (i in 0 until pet.photos.size)
//                adapter.addItem(PetPhoto(id = i, image = pet.photos[i].url))
//
//            viewModel.setNamePet(pet.name)
//            viewModel.setBirthdayPet(pet.bdate)
//            viewModel.setSexPet(if (pet.sex == "FEMALE") 0 else 1)
//
//            if(pet.inst.isNotEmpty()) stateInstagramEnabled(it.inst)
//
//            var kindTitle = activity?.intent?.extras?.getString("petKind")
//            //binding?.kids?.text = kindTitle
//
//            checkBTN()
//        }
//
//
//
//        binding?.containerKids?.setOnClickListener {
//            activity?.let { it1 -> navigation.startSelectKindsFromEditPet(it1) }
//        }
//
//        binding?.containerBreeds?.setOnClickListener {
//            if(isBreedClick){
//                activity?.let { it1 -> navigation.startSelectBreedsFromEditPet(it1) }
//            }
//            else if (!informationDialog.isAdded)
//                informationDialog.show(childFragmentManager, "informationDialog")
//        }
//
//
//        binding?.save?.setOnClickListener {
//            viewModel.editPet()
//        }
//    }
//
//    override fun initObservers() {
//        super.initObservers()
//    }
//
//}