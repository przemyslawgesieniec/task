insert into USERS (LOGIN, PASSWORD, NAME, CREATION_DATE) values
  ('user1', 'qwerty', 'User1', current_timestamp),
  ('user2', 'asdfg', 'User2', CURRENT_TIMESTAMP),
  ('user3', 'asdfgd', 'User3', CURRENT_TIMESTAMP);

insert into CATEGORIES (NAME) values
  ('IT'),
  ('Food & Drinks'),
  ('Office'),
  ('Courier'),
  ('Shop assistant');

insert into JOB_OFFERS (USER_ID, CATEGORY_ID, START_DATE, END_DATE) values
  ('1','1',current_timestamp, current_timestamp + 14),
  ('1','3',current_timestamp, current_timestamp + 14),
  ('2','1',current_timestamp, current_timestamp + 14),
  ('2','2',CURRENT_TIMESTAMP-24, CURRENT_TIMESTAMP-5);
