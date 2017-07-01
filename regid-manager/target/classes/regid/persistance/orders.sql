# start
urlConnection	= jdbc:mysql://localhost:3306/annuaireregiddb
driverName		= com.mysql.jdbc.Driver
createRegId 	= insert into annuaireregiddb.t_regId (regId) values (?);
readRegId 		= select * from annuaireregiddb.t_regId where id = ?;
updateRegId 	= update annuaireregiddb.t_regId set regId = ? where id = ?;
deleteRegId 	= delete from annuaireregiddb.t_regId where id = ?;
selectAllRegId	= select * from annuaireregiddb.t_regId;
# end