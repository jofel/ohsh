package hu.alkfejl.hermanNote.view.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hu.alkfejl.hermanNote.model.bean.User;
import hu.alkfejl.hermanNote.view.BookShopGUI;
import hu.alkfejl.hermanNote.view.Labels;

/**
 * Az oszt�ly az �j �gyf�l felv�tel�n�l megjelen� dial�gus.
 */
public class AddUserDialog extends JDialog implements ActionListener {


    private static final long serialVersionUID = 3382073646421158018L;

	private BookShopGUI gui;

    // A dial�gus azon vez�rl�it melyekre sz�ks�g lesz az esem�nykezel�s sor�n
    // oszt�lyv�ltoz�k�nt defini�ljuk
    private JTextField nameTextfield = new JTextField(10);
    private JTextField ehaTextfield = new JTextField(10);
    private JTextField roomTextfield = new JTextField(10);
    private JCheckBox kbCheck = new JCheckBox();
    private JCheckBox adminCheck = new JCheckBox();
    private JButton okButton = new JButton(Labels.ok);
    private JButton cancelButton = new JButton(Labels.cancel);

    public AddUserDialog(BookShopGUI gui, boolean modal) {
        super(gui.getWindow(), modal);
        this.gui = gui;

        // A dial�gus c�m�nek be�ll�t�sa
        this.setTitle(Labels.add_user);

        // A be�ll�t�sokat tartalmaz� panel gy�rt�sa
        JPanel settingPanel = createSettingPanel();

        // A gombokat tartalmaz� panel gy�rt�sa
        JPanel buttonPanel = createButtonPanel();

        // Az el�z� k�t panelt egy panelre rakjuk
        JPanel dialogPanel = createDialogPanel(settingPanel, buttonPanel); 

        // A dialogPanelt r�rakjuk a dial�gusra
        getContentPane().add(dialogPanel);

        // A dial�gus megfelel� m�ret�nek be�ll�t�sa (a tartalmazott elemek alapj�n)
        pack();

        // A dial�gust a BookShopGUI-hoz k�pest rajzolja ki
        setLocationRelativeTo(gui.getWindow());

        // Dialogus megjelen�t�se
        setVisible(true);
    }

    private JPanel createSettingPanel() {
        JPanel settingPanel = new JPanel();

        // A panel elrendez�se m�trix, 5 sor �s 2 oszlop, a cell�k egyforma m�ret�ek
        settingPanel.setLayout(new GridLayout(5,2));

        // Az 1. sorban egy n�v c�mke �s egy sz�vegmez� lesz
        settingPanel.add(new JLabel(Labels.user_name));
        settingPanel.add(this.nameTextfield);

        // A 2. sorban egy kor c�mke �s egy spinner lesz
        settingPanel.add(new JLabel(Labels.user_eha));
        settingPanel.add(this.ehaTextfield);
        
     // A 2. sorban egy kor c�mke �s egy spinner lesz
        settingPanel.add(new JLabel(Labels.user_room));
        settingPanel.add(this.roomTextfield);

        // A 4. sorban lesz a kedvezm�nyezetts�g
        settingPanel.add(new JLabel(Labels.user_kb));
        settingPanel.add(kbCheck);

        // A 5. sorban lesz a v�gzetts�g
        settingPanel.add(new JLabel(Labels.user_admin));
        settingPanel.add(adminCheck);

        return settingPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        // A panel elrendez�se folytonos, k�z�pre igaz�tva
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Hozz�adjuk az ok gombot, �s figyel�nk r�
        buttonPanel.add(okButton);
        okButton.addActionListener(this);

        // Hozz�adjuk a cancel gombot, �s figyel�nk r�
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(this);

        return buttonPanel;
    }

    private JPanel createDialogPanel(JPanel settingPanel, JPanel buttonPanel) {
        JPanel dialogPanel = new JPanel();

        // A panel elrendez�se BorderLayout
        dialogPanel.setLayout(new BorderLayout());

        // K�z�pen lesz a settingPanel
        dialogPanel.add(settingPanel, BorderLayout.CENTER);

        // Alul pedig a gombok
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        return dialogPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (okButton == e.getSource()) {
            // Ha az OK gombot nyomt�k meg, akkor megpr�b�ljuk felvenni az
            // �gyfelet

            if (nameTextfield.getText().isEmpty()) {
                // Ha nem adtak meg nevet, akkor egy hiba�zenetet �runk ki egy
                // error dialogra (JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.customer_name_is_required,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // l�trehozzuk a customert
            User user = new User();

            user.setName(nameTextfield.getText());
            user.setEha(ehaTextfield.getText());
            user.setRoom(Integer.parseInt(roomTextfield.getText()));
            user.setKb(kbCheck.isSelected());
            user.setAdmin(kbCheck.isSelected());

            if (!gui.getController().addUser(user)) {
                // Ha az addCustomer false-t ad vissza akkor egy hiba�zenetet
                // �runk ki egy error dialogra(JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.customer_exists,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Ha az addCustomer true-t ad vissza akkor bez�rjuk a dial�gust
                setVisible(false);
            }
        } else if (cancelButton == e.getSource()) {
            // cancel eset�n egyszer�en bez�rjuk az ablakot
            setVisible(false);
        }
    }

}

