## EventCounter - объект для учета событий реализующий следующий интерфейс:
1. Учесть событие.
2. Выдать число событий за последнюю минуту (60 секунд).
3. Выдать число событий за последний час (60 минут).
4. Выдать число событий за последние сутки (24 часа).

### 1. Программа поддерживает работу в многопоточном режиме? За счет чего? Внесите необходимые правки.

  В первой версии была использована коллекция LinkedList, потом решил воспользоваться ArrayDeque, т.к.
  он оказывается лучше подходит для данной задачи, но оба они не потоко-безопасны, поэтому была выбрана
  соответствующая коллекция ConcurrentLinkedDeque.

  Работа с потоками была успешно протетисторована (JUnit4).
   
### 2. Коллекция данных ограничена по размеру? Как? Внесите необходимые правки.

  Все выше перечисленные коллекции ограничены оперативной памятью машины. Провел тест в попытке понять сколько
  же событий могу вносить в коллекцию. Вычислил, что для моего ноута предел примерно 50 миллионов (сперва было около 30 мил,
  пришлось вручную увеличивать max heap size в настройках конфигурации). Тесты были выполнены на JUnit4.
  
