package ru.osdis.datebase;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.ResultSetMetaData;
import com.sun.javafx.binding.StringFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;

public class FXMLController implements Initializable {



    @FXML
    TextField login;

    @FXML
    PasswordField password;

    @FXML
    TextField nameDB;

    @FXML
    TextArea textAreaOne;

    @FXML
    Label consoleText;

    @FXML
    ChoiceBox choiceBox;

    @FXML
    TableView table;





    @FXML
    //подключение к БД
    private void handleButtonAction(ActionEvent event) throws SQLException {

        //проверка на пустые поля
        if(nameDB.getText().equals("")|| login.getText().equals("")||password.getText().equals(""))
        {
            consoleText.setText("ОШИБКА: Пустое поле заполнения!");
        }
        else {

            try {
                //создание подключения
                Connector connector = new Connector();
                connector.getDateBase(nameDB.getText(), login.getText(), password.getText());
                if (Connector.getConnection().isClosed() == false) {
                    consoleText.setText("ОК: Подключение прошло успешно.");
                    load();
                }
                //ловим ошибки и пишем, что подключение сорвалось из за ошибки
            }catch (Exception e){ consoleText.setText("ОШИБКА: Подключение не выполнено, проверьте введенные данные!");
        }

      }
    }



    //добавляем в CoiceBox название таблиц.
        private  void load() throws SQLException {


                       ArrayList<String> tab = new ArrayList<>();
                       DatabaseMetaData dbmd = (DatabaseMetaData) Connector.getConnection().getMetaData();
                        ResultSet rs = dbmd.getTables(null, null, null, null);
                        while (rs.next()) {
                                tab.add(rs.getString("TABLE_NAME"));
                            }

                       choiceBox.setItems(FXCollections.observableArrayList(tab));

                        //закрываем
                        rs.close();

            }



    @FXML
    //вывод содержимого таблицы
    private void drawDB(ActionEvent event) throws SQLException {

        if(Connector.getConnection() == null){
                 consoleText.setText("ОШИБКА: Нет соединения! Проверьте настройки.");
        }
        else
        {

             Statement statement = Connector.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("Select * from " + choiceBox.getValue());

             ResultSetMetaData data = (ResultSetMetaData) resultSet.getMetaData();

            //очищаем зону текста (вывода)
              textAreaOne.setText("");

                  //head table
                  int numColums = data.getColumnCount();
                  for(int i = 1; i < numColums; i++){
                  textAreaOne.appendText(String.format("|%-22s","\t" + data.getColumnName(i)));
                  }

                  textAreaOne.appendText("\n");

            //вся информация из таблицы
              while (resultSet.next()) {

                  for (int i = 1; i < numColums; i++) {
                      textAreaOne.appendText(String.format("|%-22s", resultSet.getString(i)+"\t"));
                  }

                  textAreaOne.appendText("\n");
              }
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
