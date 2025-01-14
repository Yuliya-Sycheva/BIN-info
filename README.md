# BIN-info
BIN-info - приложение для поиска информации по банковской карте по BIN



## Функции приложения

### Поиск информации о карте по BIN
Поиск выполняется по первым 6-8 цифрам (BIN) номера карты, введенным в строку поиска.
Показывается карточка с информацией о карте.
Есть возможность перейти в соответствующие приложения при нажатии на координаты, сайт и номер телефона банка.

<div style="display: flex; justify-content: space-around;">
<img src="https://github.com/Yuliya-Sycheva/BIN-info/blob/dev/%D0%BD%D0%B0%D1%87%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9%20%D1%8D%D0%BA%D1%80%D0%B0%D0%BD.jpg?raw=true" width=30% height=30%>
<img src="https://github.com/Yuliya-Sycheva/BIN-info/blob/dev/%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BA%D0%B0.jpg?raw=true" width=30% height=30%>
<img src="https://github.com/Yuliya-Sycheva/BIN-info/blob/dev/%D0%BA%D0%B0%D1%80%D1%82%D1%8B.jpg?raw=true" width=30% height=30%>
</div>

### История запросов
Успешно найденные карты сохраняются и показываются на данном экране.

<img src="https://github.com/Yuliya-Sycheva/BIN-info/blob/dev/%D0%B8%D1%81%D1%82%D0%BE%D1%80%D0%B8%D1%8F.jpg?raw=true" width=30% height=30%>

### Уведомление об ошибках
Если что-то пошло не так, приложение отображает соответствующие ошибки

<div style="display: flex; justify-content: space-around;">
<img src="https://github.com/Yuliya-Sycheva/BIN-info/blob/dev/%D0%BE%D1%88%D0%B8%D0%B1%D0%BA%D0%B01.jpg?raw=true" width=30% height=30%>
<img src="https://github.com/Yuliya-Sycheva/BIN-info/blob/dev/%D0%BE%D1%88%D0%B8%D0%B1%D0%BA%D0%B02.jpg?raw=true" width=30% height=30%>
<img src="https://github.com/Yuliya-Sycheva/BIN-info/blob/dev/toast.png?raw=true" width=30% height=30%>
</div>

## Стек
Kotlin, Android SDK, Retrofit, Room, Coroutines Flow, MVVM, Koin, Jetpack Navigation Component, RecyclerView, LiveData, Intent, ViewBinding

## Общие требования
Приложение поддерживает устройства, начиная с Android 10 (minSdkVersion = 29)