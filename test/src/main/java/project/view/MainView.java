package project.view;

import project.log.json.JsonLog;
import project.log.json.JsonToLogParser;
import project.log.repository.LogRepository;
import project.log.service.LogService;
import project.log.task.LogTask;
import project.thread.LogManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class MainView extends JFrame {

    public MainView(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter filepath");
        final JTextField tf = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        panel.add(label);
        panel.add(label);
        panel.add(tf);
        panel.add(submitButton);

        final JTextArea ta = new JTextArea();

        this.getContentPane().add(BorderLayout.CENTER, panel);
        this.getContentPane().add(BorderLayout.SOUTH, ta);
        this.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JsonToLogParser jsonToLogParser = new JsonToLogParser();
                LogService logService = new LogService(new LogRepository());
                List<JsonLog> listOfLogsFromJson = jsonToLogParser.loadFileFromPath(tf.getText());
                if(listOfLogsFromJson != null){
                    Map<String, List<JsonLog>> groupLogs = jsonToLogParser.groupJsonLog(listOfLogsFromJson);
                    List<LogTask> listOfTasks = logService.getTasksBasedOnLogs(groupLogs);
                    LogManager.queue.addAll(listOfTasks);
                } else{
                    ta.setText("Something went wrong. Try again");
                }


            }
        });
    }
}
