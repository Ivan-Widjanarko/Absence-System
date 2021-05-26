package tugasIndividu;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * Class for Absence
 *
 * @author Ivan Widjanarko
 * @version 26-05-2021
 */
public class Absence {
	private static JTable table;
	private static DefaultTableModel model;
	private static JTextField textFieldName;
	
	public static void main(String[] args) {
		showWindow();
	}
	
	public static void showWindow() {
		JFrame frame = new JFrame("Absence System");
		frame.setBounds(100,100,600,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		model = new DefaultTableModel();
		Object[] column = {"Name", "Date", "Start Time", "End Time"};
		final Object[] row = new Object[4];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableModel tableModel = table.getModel();
        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++)
        {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
        table.setAutoCreateRowSorter(true);
        
		JLabel lblName = new JLabel("Name :");
		
		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		
		JLabel lblDate = new JLabel("Date :");
		
		Date today = new Date();
		JSpinner spinnerDate = new JSpinner(new SpinnerDateModel(today, null, null, Calendar.MONTH));
		SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MMMM/yyyy");
        Date currentDate = (Date) spinnerDate.getValue();
        String dateText = dateFormat.format(currentDate);
        row[1] = dateText;
	    JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerDate, "dd/MMMM/yyyy");
	    spinnerDate.setEditor(editor);
		
		JLabel lblTimeStart = new JLabel("Start Time :");
		
		Date dateStart = new Date();
		SpinnerDateModel smStart = new SpinnerDateModel(dateStart, null, null, Calendar.HOUR_OF_DAY);
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss z");
		JSpinner spinnerTimeStart = new JSpinner(smStart);
		Date currentTimeStart = (Date) spinnerTimeStart.getValue();
        String timeStartText = timeFormat.format(currentTimeStart);
        row[2] = timeStartText;
		JSpinner.DateEditor deStart = new JSpinner.DateEditor(spinnerTimeStart, "HH:mm:ss");
		spinnerTimeStart.setEditor(deStart);
		
		JLabel lblTimeEnd = new JLabel("End Time :");
		
		Date dateEnd = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dateEnd); 
		c.add(Calendar.HOUR, 1);
		dateEnd = c.getTime();
		SpinnerDateModel smEnd = new SpinnerDateModel(dateEnd, null, null, Calendar.HOUR_OF_DAY);
		JSpinner spinnerTimeEnd = new JSpinner(smEnd);
		Date currentTimeEnd = (Date) spinnerTimeEnd.getValue();
        String timeEndText = timeFormat.format(currentTimeEnd);
        row[3] = timeEndText;
		JSpinner.DateEditor deEnd = new JSpinner.DateEditor(spinnerTimeEnd, "HH:mm:ss");
		spinnerTimeEnd.setEditor(deEnd);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldName.getText().equals("")){
			        try {
			        	JOptionPane.showMessageDialog(null, "Please Fill The Name", "Empty Name", JOptionPane.ERROR_MESSAGE);
			        }
			        
			        catch (Exception e1) {
			            e1.printStackTrace();
			        }
			        
			        finally {
			        	textFieldName.setText("");
			        }
				}
				else {
					row[0] = textFieldName.getText();
					
					try {
						row[0] = textFieldName.getText();
					}
					catch (IncorrectNameException errorMessage) {
						JOptionPane.showMessageDialog(null, "Name is Incorrect", "Incorrect Name", JOptionPane.ERROR_MESSAGE);
					}
					
					spinnerDate.addChangeListener(evt -> {
						try {
							Date currentDate = (Date) spinnerDate.getValue();
				            String dateText = dateFormat.format(currentDate);
							row[1] = dateText;
						}
						catch (DateTimeFormatException errorMessage) {
							JOptionPane.showMessageDialog(null, "Date Format is Incorrect", "Incorrect Date Format", JOptionPane.ERROR_MESSAGE);
						}
			            
			        });
					
					spinnerTimeStart.addChangeListener(evt -> {
						try {
							Date currentTimeStart = (Date) spinnerTimeStart.getValue();
					        String timeStartText = timeFormat.format(currentTimeStart);
					        row[2] = timeStartText;
						}
						catch (DateTimeFormatException errorMessage) {
							JOptionPane.showMessageDialog(null, "Time Format is Incorrect", "Incorrect Time Format", JOptionPane.ERROR_MESSAGE);
						}
					});
					spinnerTimeEnd.addChangeListener(evt -> {
						try {
							Date currentTimeEnd = (Date) spinnerTimeEnd.getValue();
					        String timeEndText = timeFormat.format(currentTimeEnd);
					        row[3] = timeEndText;
						}
						catch (DateTimeFormatException errorMessage) {
							JOptionPane.showMessageDialog(null, "Time Format is Incorrect", "Incorrect Time Format", JOptionPane.ERROR_MESSAGE);
						}
						
					});
					
					try {
						Date date = new SimpleDateFormat("E dd/MMMM/yyyy").parse((String) row[1]);
			            Date start = new SimpleDateFormat("HH:mm:ss z").parse((String) row[2]);
			            Date end = new SimpleDateFormat("HH:mm:ss z").parse((String) row[3]);

			            if (start.compareTo(end) < 0 && date.compareTo(currentDate) <= 0) {
			            	model.addRow(row);
							
							textFieldName.setText("");
							
							JOptionPane.showMessageDialog(null, "Data Has Been Added Successfuly", "Add Data", JOptionPane.INFORMATION_MESSAGE);
			            }
			            
			            else if (start.compareTo(end) > 0) {
			            	try {
					        	JOptionPane.showMessageDialog(null, "End Time is Before Start Time", "Smaller End Time", JOptionPane.ERROR_MESSAGE);
					        } catch (Exception e1) {
					            e1.printStackTrace();
					        }
			            } 
			            
			            else if (start.compareTo(end) == 0) {
			            	try {
					        	JOptionPane.showMessageDialog(null, "End Time is Same as Start Time", "Start Time Same As End Time", JOptionPane.ERROR_MESSAGE);
					        } catch (Exception e1) {
					            e1.printStackTrace();
					        }
			            }
			            
			            else if (date.compareTo(currentDate) > 0) {
			            	try {
					        	JOptionPane.showMessageDialog(null, "Date is After Today", "Incorrect Date", JOptionPane.ERROR_MESSAGE);
					        } catch (Exception e1) {
					            e1.printStackTrace();
					        }
			            }
			            
			            else {
			            	JOptionPane.showMessageDialog(null, "Something Weird Happened...", "Error", JOptionPane.ERROR_MESSAGE);
			            }
			        } catch (ParseException e1) {
			            e1.printStackTrace();
			        }
				}
			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				if (i>=0) {
					try {
						model.removeRow(i);
						JOptionPane.showMessageDialog(null, "Data Has Been Deleted Successfully", "Delete Data", JOptionPane.INFORMATION_MESSAGE);
			        } catch (Exception e1) {
			            e1.printStackTrace();
			        }
				}
				else {
					JOptionPane.showMessageDialog(null, "Please Select the Row You Want to Delete", "Unselected Row", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDate, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(spinnerDate, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTimeStart, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(spinnerTimeStart, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTimeEnd, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(spinnerTimeEnd, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(31)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)))
					.addGap(9)
					.addComponent(scrollPane)
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(20)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinnerDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(20)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTimeStart, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinnerTimeStart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(20)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTimeEnd, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinnerTimeEnd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(43)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
						.addComponent(scrollPane))
					.addGap(0))
		);
		frame.getContentPane().setLayout(groupLayout);
		
		frame.setVisible(true);
		
	}

}
