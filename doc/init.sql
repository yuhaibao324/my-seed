--日志表
CREATE TABLE sys_log
(
  id serial primary key,
  uri varchar,
  method varchar,
  user_id integer,
  req_params varchar,
  success boolean,
  req_time timestamp,
  err_msg varchar,
  host varchar
);
create table sys_perm_space(
	id serial primary key,
	name varchar
);
--权限表
CREATE TABLE sys_perm
(
  id serial primary key,
  note varchar,
  pattern varchar,
  space_id integer,
  uri_id integer
);
--角色表
CREATE TABLE sys_role
(
  id serial primary key,
  name varchar,
  valid boolean default true,
  space_id integer
);
--角色权限关联表
CREATE TABLE sys_role_perm_mid
(
  perm_id integer,
  role_id integer
);
--用户表
CREATE TABLE sys_user
(
  id serial primary key,
  name varchar,
  password varchar NOT NULL,
  nick varchar,
  create_time timestamp default now(),
  valid boolean default true,
  space_id integer
);
--用户角色关联表
  CREATE TABLE sys_user_role_mid
(
  user_id integer,
  role_id integer
);
--用户权限关联表
create table sys_user_perm_mid(
	user_id integer,
	perm_id integer
);
--和sys_perm是1对多关系
create table sys_perm_uri(
	id serial primary key,
	method varchar,
	uri varchar,
	note varchar
);
create table sys_menu(
	id serial primary key,
	name varchar,
	parent_id integer,
	seq integer
);
create table sys_role_menu_mid(
	role_id integer,
	menu_id integer
);
