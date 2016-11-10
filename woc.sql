create table t_category(cid int primary key not null auto_increment,cName varchar(255));

insert into t_category(cName) values("000:副本0");
insert into t_category(cName) values("111:副本1");


create table t_article(id int primary key not null auto_increment, categoryId int, title varchar(255), c_desc varchar(255), img varchar(255), file varchar(255),foreign key(categoryId) references t_category(cid) on delete cascade on update cascade);


insert into t_article(categoryId,title,c_desc,img,file) values(1,"标题0-1","描述0-1","图片0-1","文件0-1");
insert into t_article(categoryId,title,c_desc,img,file) values(1,"标题0-2","描述0-2","图片0-2","文件0-2");
insert into t_article(categoryId,title,c_desc,img,file) values(2,"标题1-1","描述1-1","图片1-1","文件1-1");
insert into t_article(categoryId,title,c_desc,img,file) values(2,"标题1-2","描述1-2","图片1-2","文件1-2");

create table t_file(uuid varchar(255) primary key, file varchar(255));
