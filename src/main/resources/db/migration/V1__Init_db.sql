create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values ( 1 );

create table image (
  id bigint not null,
  filename varchar(255),
  name varchar(255) not null,
  author_id bigint,
  primary key (id)
);

create table user_role (
  user_id bigint not null,
  role varchar(255)
);

create table usr (
  id bigint not null,
  active bit not null,
  password varchar(255) not null,
  username varchar(255) not null ,
  primary key (id)
);

alter table image
  add constraint image_user_fk
  foreign key (author_id) references usr (id);

alter table user_role
  add constraint user_role_user_fk
  foreign key (user_id) references usr (id);