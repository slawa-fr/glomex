import arduino.Arduino;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jssc.SerialPortException;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Properties;


// https://habr.com/ru/articles/340630/
// Эксперимент 2
// Зачем нужно соединять Java-программу на компьютере и Arduino?
// https://temofeev.ru/info/articles/zachem-nuzhno-soedinyat-java-programmu-na-kompyutere-i-arduino/



public class Controller extends Component {

    private  static final String CURRENTDIRECTORY = "user.dir";
    private String comPortNumber;
    Arduino arduino;
    private boolean connected;
    private String lamp;

    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15, button16, button17, button18, button19;

    @FXML
    private Label myLabel1, myLabel2, myLabel3, myLabel4, myLabel5;

    @FXML
    private TextField textField1, textField2, textField3, textField4, textField5, textField6, textField7, textField8, textField9, textField10;

    @FXML
    private ImageView imageA1, imageA2, imageA3, imageA4, imageA5, imageA6, imageA7, imageA8, imageA9, imageA10, imageA11;

    @FXML
    void initialize() {

// Проверка существования файла setting.properties
        File theDir1 = new File(System.getProperty(CURRENTDIRECTORY),"setting.properties");

        if (!theDir1.exists()){
            createFile1();
        }

// Проверим есть ли файл setting.properties
        checkingFile();

// Загружаем setting.properties
        File theDir = new File(System.getProperty(CURRENTDIRECTORY),"setting.properties");
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(theDir));
        } catch (IOException e) {
            e.printStackTrace();
        }

// Получить значение comPortNumber и lamp из setting.properties"
        comPortNumber = appProps.getProperty("comPortNumber", "COM4");
        lamp = appProps.getProperty("lamp", "48");
        //System.out.println("читаем из пропертис lamp = " + lamp);
// Установить в textField comPortNumber
        textField1.setText(String.valueOf(comPortNumber));

        //Arduino arduino = new Arduino("COM4", 9600);
        arduino = new Arduino(comPortNumber, 115200);
        connected = arduino.openConnection();
        myLabel1.setText("Порт открыт");
        imageA1.setImage(new Image("button_grey.png"));

//Нажатие на кнопку button1 (Открыть порт) - начало
        button1.setOnAction(event -> {
            connected = arduino.openConnection();
            System.out.println("connected: " + connected);
            System.out.println("Порт открыт");
            myLabel1.setText("Порт открыт");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
//Нажатие на кнопку button1 (Открыть порт) - конец

//Нажатие на кнопку button2 (Arduino OFF)- начало
        button2.setOnAction(event -> {

            try {
                arduino.serialWrite('0');
                arduino.closeConnection();
                System.out.println("connected: " + connected);
                myLabel1.setText("Порт закрыт");
            } catch (NullPointerException e) {
                System.out.println("connected: " + connected);
                System.out.println("Порт закрыт");
            }
            myLabel1.setText("Соединение разорвано");
        });
//Нажатие на кнопку button2 (Arduino OFF)- конец

//Нажатие на кнопку button3 (Сохранить) - начало
        button3.setOnAction(event -> {

            comPortNumber = textField1.getText();
            saveToPropertiesSetting();
            arduino = new Arduino(comPortNumber, 115200);

// Эксперимент 2
            SerialPort[] ports = SerialPort.getCommPorts();

            for (SerialPort port: ports) {
                System.out.println(port.getSystemPortName());
            }


        });
//Нажатие на кнопку button3 (Сохранить) - конец

// Азимут
//Нажатие на кнопку button4 (Влево (на Восток) - начало
        button4.setOnAction(event -> {

            try {
                arduino.serialWrite('4');
                lamp = "49";
                myLabel2.setText("Влево (на Восток)");

            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button4 (Влево (на Восток)) - конец


//Нажатие на кнопку button5 (-1) - начало
        button5.setOnAction(event -> {

            try {
                arduino.serialWrite('5');
                lamp = "49";
                myLabel2.setText("Влево (на Восток) на 1 шаг");
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button5 (-1) - конец

//Нажатие на кнопку button6 (Стоп) - начало
        button6.setOnAction(event -> {

            try {
                arduino.serialWrite('6');
                lamp = "48";
                myLabel2.setText("Стоп");
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button5 (Стоп) - конец

//Нажатие на кнопку button7 (+1) - начало
        button7.setOnAction(event -> {

            try {

                arduino.serialWrite('7');
                lamp = "49";
                myLabel2.setText("Вправо (на Запад) на 1 шаг");
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button7 (+1) - конец


//Нажатие на кнопку button8 (Вправо (на Запад)) - начало
        button8.setOnAction(event -> {

            try {
                arduino.serialWrite('8');
                lamp = "49";
                myLabel2.setText("Вправо (на Запад)");
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button8 (Вправо (на Запад)) - конец







// Угол места
//Нажатие на кнопку button9 (Вниз) - начало
        button9.setOnAction(event -> {

            try {
                arduino.serialWrite('d');
                lamp = "49";
                myLabel2.setText("Вниз");

            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button9 (Вниз)) - конец


//Нажатие на кнопку button10 (-1) - начало
        button10.setOnAction(event -> {

            try {
                arduino.serialWrite('f');
                lamp = "49";
                myLabel2.setText("Вниз на 1 шаг");
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button10 (-1) - конец


//Нажатие на кнопку button12 (+1) - начало
        button12.setOnAction(event -> {

            try {

                arduino.serialWrite('h');
                lamp = "49";
                myLabel2.setText("Вверх на 1 шаг");
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button12 (+1) - конец


//Нажатие на кнопку button13 (Вверх)) - начало
        button13.setOnAction(event -> {

            try {
                arduino.serialWrite('j');
                lamp = "49";
                myLabel2.setText("Вверх");
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button13 (Вверх)) - конец




// Поляризация
//Нажатие на кнопку button16 (Влево) - начало
        button16.setOnAction(event -> {

            try {
                arduino.serialWrite('r');
                lamp = "49";
                myLabel2.setText("Влево");

            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button9 (Вниз)) - конец


//Нажатие на кнопку button17 (-1) - начало
        button17.setOnAction(event -> {

            try {
                arduino.serialWrite('t');
                lamp = "49";
                myLabel2.setText("Влево на 1 шаг");
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button17 (-1) - конец


//Нажатие на кнопку button18 (+1) - начало
        button18.setOnAction(event -> {

            try {

                arduino.serialWrite('u');
                lamp = "49";
                myLabel2.setText("Вправо на 1 шаг");
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button18 (+1) - конец


//Нажатие на кнопку button19 (Вправо)) - начало
        button19.setOnAction(event -> {

            try {
                arduino.serialWrite('i');
                lamp = "49";
                myLabel2.setText("Вправо");
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button13 (Вверх)) - конец




//Нажатие на кнопку button14 (Питание на двигатели ВКЛ.) - начало
        button14.setOnAction(event -> {
            try {
                arduino.serialWrite('1');
                imageA1.setImage(new Image("button_green.png"));
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button14 (Питание на двигатели ВКЛ.) - конец

//Нажатие на кнопку button15 (Питание на двигатели ВЫКЛ.) - начало
        button15.setOnAction(event -> {
            try {
                arduino.serialWrite('0');
                imageA1.setImage(new Image("button_grey.png"));
            } catch (NullPointerException e) {
                System.out.println("Нет связи с Arduino");
                myLabel2.setText("Нет связи с Arduino");
            }
        });
//Нажатие на кнопку button15 (Питание на двигатели ВЫКЛ.) - конец

    }

    void createFile1(){
        File file1 = null;
        String resource = "/setting.properties";
        URL res = getClass().getResource(resource);
        if (res.getProtocol().equals("jar")) {
            try {
                InputStream input = getClass().getResourceAsStream(resource);
                file1 = File.createTempFile("setting", ".properties");
                OutputStream out = new FileOutputStream(file1);
                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.close();
                file1.deleteOnExit();
            } catch (IOException ex) {
                //Exceptions.printStackTrace(ex);
                System.out.println("Exceptions.printStackTrace(ex);");
            }
        } else {
            file1 = new File(res.getFile());
        }

        if (file1 != null && !file1.exists()) {
            throw new RuntimeException("Error: File " + file1 + " not found!");
        }

        File file2 = new File(System.getProperty(CURRENTDIRECTORY),"setting.properties");
        try {
            copyFileUsingStream(file1, file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Как скопировать файл в Java? 4 способа — примеры и код
// Способ 1: Используем потоки для копирования файла
// https://javadevblog.com/kak-skopirovat-fajl-v-java-4-sposoba-primery-i-kod.html
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    // Проверка существования файла setting.properties
    void checkingFile(){
        File theDir = new File(System.getProperty(CURRENTDIRECTORY),"setting.properties");
        if (!theDir.exists())
            createFileAppProperties();
    }

    // Метод создания файла setting.properties из папки с ресурсами в рабочую папку с программой
    void createFileAppProperties(){
// Создаем файл setting.properties в папке с программой
        File dest = new File(System.getProperty(CURRENTDIRECTORY),"setting.properties");
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("setting.properties");
             OutputStream out = new FileOutputStream(dest)) {
            int data;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    // Метод сохранения в properties
    void saveToPropertiesSetting() {
        //System.out.println("Сработал метод: saveToPropertiesSetting()");
// Загружаем  setting.properties из папки database
        File theDir17 = new File(System.getProperty(CURRENTDIRECTORY),"setting.properties");
        Properties appProps = new Properties();
        try {
            appProps.load(new BufferedReader(new InputStreamReader(new FileInputStream(theDir17), "UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        comPortNumber = textField1.getText();

        appProps.setProperty("comPortNumber", String.valueOf(comPortNumber));
        appProps.setProperty("lamp", String.valueOf(lamp));
        //System.out.println("lamp = " + lamp);

// Сохраним в setting.properties внесенные изменения из текстовых полей
        String newAppProps = "setting.properties";
        try {
            appProps.store(new FileWriter(newAppProps), "store");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    EventListener w = new  EventListener();

    }

// https://www.ap-impulse.com/com-port-opros-i-vyvod-dannyx-na-java-shag-46/

//class EventListener implements SerialPortEventListener { /*Слушатель срабатывающий по появлению данных на COM-порт*/
//    public void serialEvent(SerialPortEvent event) {
//        if (event.isRXCHAR() && event.getEventValue() > 0) { /*Если происходит событие установленной маски и количество байтов в буфере более 0*/
//            try {
//                String data = serialPort.readString(event.getEventValue()); /*Создаем строковую переменную  data, куда и сохраняем данные*/
//                System.out.print(data);/*Выводим данные на консоль*/
//            } catch (SerialPortException ex) {
//                System.out.println(ex);
//            }
//        }
//    }
//
//    @Override
//    public void serialEvent(gnu.io.SerialPortEvent serialPortEvent) {
//
//    }
//}








