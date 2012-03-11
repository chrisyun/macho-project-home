
drop index IDX_ISSHARE_FAVORITE;

drop index IDX_OWNER_FAVORITE;

/*==============================================================*/
/* Index: IDX_OWNER_FAVORITE                                    */
/*==============================================================*/
create unique index IDX_OWNER_FAVORITE on DM_FAVORITE (
   OWNER ASC
);

/*==============================================================*/
/* Index: IDX_ISSHARE_FAVORITE                                  */
/*==============================================================*/
create unique index IDX_ISSHARE_FAVORITE on DM_FAVORITE (
   ISSHARE ASC
);
