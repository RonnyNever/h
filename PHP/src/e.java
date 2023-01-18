
public class e {
	
	<?php


			class DatabaseConnection {

				private $connection;
					
				public function __construct(){

					// Zugangsdaten
					$host = '127.0.0.1';
					$db = 'wt1uebung';
					$user = 'wt1_ueb';
					$pw = 'abcd';
					// DSN Verbindung mittels PDO
					$dsn = "mysql:host=$host;port=3306;dbname=$db";
					// Optionen, die ich Einstellen kann
					$options = [
						PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
						PDO::ATTR_CASE => PDO::CASE_NATURAL
					];
					// Objekt mittels Optionen und Credetials ( Referenzen ) erzeugen
					try{
						$this->connection = new PDO($dsn, $user, $pw, $options);
					} 
					catch(PDOException $e){
						echo "Verbindungsaufbau gescheitert: " . $e->getMessage();
					}
				}
				
				public function __destruct(){
					$this->connection = NULL;	
				}
				
				public function get_highscores() {
					$highscores = [];
					$sql = "SELECT username, points FROM Score ORDER BY points DESC;";	// SQL
					$highscores = $this->connection->query($sql)->fetchAll(PDO::FETCH_CLASS, 'Score'); // Methode zum lesen der Daten 

					return $highscores;
				}
				
				public function add_highscore($username, $points){
					
					if ($username == NULL || empty($username)){ // Gucken ob leer
						return false;
					}
					
					if ($points == NULL || empty($points) || intval($points) <= 0){ // Gucken ob leer
						return false;
					}
					
					$sql = "INSERT INTO Score (id, username, points) VALUES (NULL, ?, ?);";		// SQL + Prepared Statments
					$statement = $this->connection->prepare($sql); // Methode zum schreiben der Datein
					$result = $statement->execute([$username, $points]);
					return $result;
				}
				
			}



			?>


}
