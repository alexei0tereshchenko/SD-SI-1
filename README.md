# SD-SI-1

Необходимо сделать 4 операции: создание обьекта,изменение,удаление и кенсел/реджект/терминей(в зависимости от обьектоного типа 
и доступных значений  статуса объекта)

1 Crete object: необходимо сделать ограничение по количеству объектов (5штук) под одним биллинг аккаунтом  

2 Update : можно изменять объекто если его статус :
   *  для била (created,pending cancelletion)
   *  для аджастмента (pending ,approved)
   *   для пейментов (pending, posted)
   *   для диспутов (pending ,approved)
   *  Для ots (created,unbilled, billed)

3 delete : пока без ограничений  

4 cancel/reject/terminate: меняет статус обьекта на соответвующий  

ограничения по статусам :
  *  для била (pending cancelletion)
  *    для аджастмента (approved)
  *    для пейментов (posted)
  *    для диспутов (approved)
  *    Для ots ( billed)
