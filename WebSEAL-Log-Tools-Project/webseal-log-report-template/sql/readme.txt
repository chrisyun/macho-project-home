db2 connect to eventxml user timldap using password
db2 -tvsf create_view.sql

db2 connect to tamldap user tamldap using password
db2 -tvsf tamldap_pwd_policy.sql
