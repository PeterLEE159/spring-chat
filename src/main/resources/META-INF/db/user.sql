create table tb_user (
	userid varchar(20) primary key,
	userpwd varchar(20),
	username varchar(100)
);

insert into tb_user values('hong', 'zxcv1234', '홍길동');
insert into tb_user values('kim', 'zxcv1234', '김유신');
insert into tb_user values('lee', 'zxcv1234', '이순신');
insert into tb_user values('kang', 'zxcv1234', '강감찬');

commit;