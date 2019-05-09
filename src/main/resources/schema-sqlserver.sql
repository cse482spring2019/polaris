IF NOT EXISTS (SELECT 1 FROM sysobjects WHERE NAME='stop' and XTYPE='U')
  CREATE TABLE stops (
    id      INT           IDENTITY  PRIMARY KEY,
  );
