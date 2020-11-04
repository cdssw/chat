-- user
insert into user(id, username, user_nm, user_nick_nm, phone, time_stamp) values
(1, 'cdssw@naver.com', 'Andrew', 'Blue', '010-1111-1111', now())
, (2, 'loh002@naver.com', 'Monica', 'Red','010-1111-1111', now())
, (3, 'michael@naver.com', 'Michael', 'Green','010-1111-1111', now());

-- chat
insert into chat(leader_name, sender, receiver, meet_id, message, read_yn, time_stamp) values
  ('cdssw@naver.com', 'loh002@naver.com', 'cdssw@naver.com',  1, 'msg 1', false, now())
, ('cdssw@naver.com', 'loh002@naver.com', 'cdssw@naver.com', 1, 'msg 2', false, now())
, ('cdssw@naver.com', 'loh002@naver.com', 'cdssw@naver.com', 1, 'msg 3', false, now())
, ('cdssw@naver.com', 'loh002@naver.com', 'cdssw@naver.com', 1, 'msg 4', false, now())
, ('cdssw@naver.com', 'loh002@naver.com', 'cdssw@naver.com', 1, 'msg 5', false, now())
, ('cdssw@naver.com', 'loh002@naver.com', 'cdssw@naver.com', 1, 'msg 6', false, now())
, ('cdssw@naver.com', 'loh002@naver.com', 'cdssw@naver.com', 1, 'msg 7', false, now())
, ('cdssw@naver.com', 'loh002@naver.com', 'cdssw@naver.com', 1, 'msg 8', false, now())
, ('cdssw@naver.com', 'loh002@naver.com', 'cdssw@naver.com', 1, 'msg 9', false, now())
, ('cdssw@naver.com', 'cdssw@naver.com', 'loh002@naver.com', 1, 'msg 10', false, now())
, ('cdssw@naver.com', 'cdssw@naver.com', 'loh002@naver.com', 1, 'msg 11', false, now())
, ('cdssw@naver.com', 'cdssw@naver.com', 'loh002@naver.com', 1, 'msg 12', false, now())
, ('cdssw@naver.com', 'cdssw@naver.com', 'loh002@naver.com', 1, 'msg 13', false, now())
, ('cdssw@naver.com', 'cdssw@naver.com', 'loh002@naver.com', 1, 'msg 14', false, now())
, ('cdssw@naver.com', 'cdssw@naver.com', 'loh002@naver.com', 1, 'msg 15', false, now())
, ('cdssw@naver.com', 'cdssw@naver.com', 'loh002@naver.com', 1, 'msg 16', false, now())
, ('cdssw@naver.com', 'michael@naver.com', 'cdssw@naver.com', 1, 'msg 17', false, now())
, ('cdssw@naver.com', 'michael@naver.com', 'cdssw@naver.com', 1, 'msg 18', false, now())
;