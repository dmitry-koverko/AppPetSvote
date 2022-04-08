###  UI theme state day
![dark](https://github.com/dmitry-koverko/AppPetSvote/blob/dev/src/images/register.png?raw=true "Ок")

###  UI theme state night
![dark](https://github.com/dmitry-koverko/AppPetSvote/blob/dev/src/images/register-dark.png?raw=true "Ок")

###  Fragment state
- по умолчаннию (все кнопки аидны и кликабельны)
- прогрессбар (виден, пока не произойдет регистрация юзера или ошибка)
    1. При успешной регистрации перекидываем SplashFragment, где происходит обновление данных
    2. При ошибке возвращаем в дефолтное состояние

### Use cases
- RegisterUserUseCase (params: RegisterUserParams)

###  General
![dark](https://github.com/dmitry-koverko/AppPetSvote/blob/dev/src/images/register_general.png?raw=true "Ок")
