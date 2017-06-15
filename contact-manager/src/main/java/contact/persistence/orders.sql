# start
urlConnection	= jdbc:mysql://localhost:3306/exampledatabase
driverName		= com.mysql.jdbc.Driver
login			= root
password		= admin
createPersonne = insert into exampledatabase.personne (nom, prenom, email) values (?, ?, ?);
readPersonne = select * from exampledatabase.personne where id = ?;
updatePersonne = update exampledatabase.personne set nom = ?, prenom = ?, email = ? where id = ?;
deletePersonne = delete from exampledatabase.personne where id = ?;
selectAllPersonne = select * from exampledatabase.personne;
# end