/*
 * package userinterface; import javax.swing.*; import java.awt.*; import
 * java.awt.event.*; public class ReviewInterface extends JPanel{
 * 
 * JPanel labelPanel = new JPanel(); JPanel foldablePanel=new JPanel(); JPanel
 * mainPanel = new JPanel(); String fileDate =new String("17.AUG.16"); int
 * index=0; public ReviewInterface() {
 * 
 * //MainFrame.singleWords=separateWords(MainFrame.wordGroup);
 * labelPanel.setBounds(0, 0, 288, 600); //foldablePanel.setBounds(0, 0, 288,
 * 600); mainPanel.setBounds(288, 0, 864, 600); mainPanel.setLayout(null);
 * labelPanel.setBackground(MainFrame.secondBackgroundColor); //
 * foldablePanel.setBackground(MainFrame.secondBackgroundColor);
 * mainPanel.setBackground(MainFrame.mainBackgroundColor); JButton
 * labelListSwitch=UserInterface.createButton
 * (fileDate,0,450,180,30,MainFrame.secondBackgroundColor,new
 * Color(155,155,155,255)); //�����Ǹ��ǵ����б��� mainPanel.add(labelListSwitch);
 * JButton j1=new JButton("J1"); JButton j2=new JButton("J2");
 * j1.setSize(50,50); j2.setSize(50,50); labelPanel.add(j1); labelPanel.add(j2);
 * //labelPanel.add(foldablePanel); add(labelPanel); add(mainPanel);
 * setBounds(0, 0, 1152, 600); setLayout(null); }
 * 
 * private String[] separateWords(String group) {
 * 
 * String[] singleWords =new String[100]; int index=0; int beginPoint=0; int
 * endPoint=0; endPoint=group.indexOf(124,beginPoint);//124��Ӧ�ķ�����"|"
 * while(endPoint!=-1) {
 * singleWords[index]=group.substring(beginPoint,endPoint);
 * beginPoint=++endPoint; index++; endPoint=group.indexOf(124,beginPoint);
 * 
 * } this.index=index; return singleWords;
 * 
 * } private void setLabel(String[] s) { int i=0; JButton[] labelWords = new
 * JButton[100]; while(i<index) { labelWords[i]=new JButton(singleWords[i]);
 * labelWords[i].setFont(MainFrame.labelButtonFont);
 * labelWords[i].setSize(50,50); labelWords[i].setForeground(Color.BLACK);
 * foldablePanel.add(labelWords[i]); i++;
 * 
 * } } }
 */