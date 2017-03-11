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
 * Az osztály az új ügyfél felvételénél megjelenõ dialógus.
 */
public class AddUserDialog extends JDialog implements ActionListener {


    private static final long serialVersionUID = 3382073646421158018L;

	private BookShopGUI gui;

    // A dialógus azon vezérlõit melyekre szükség lesz az eseménykezelés során
    // osztályváltozóként definiáljuk
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

        // A dialógus címének beállítása
        this.setTitle(Labels.add_user);

        // A beállításokat tartalmazó panel gyártása
        JPanel settingPanel = createSettingPanel();

        // A gombokat tartalmazó panel gyártása
        JPanel buttonPanel = createButtonPanel();

        // Az elõzõ két panelt egy panelre rakjuk
        JPanel dialogPanel = createDialogPanel(settingPanel, buttonPanel); 

        // A dialogPanelt rárakjuk a dialógusra
        getContentPane().add(dialogPanel);

        // A dialógus megfelelõ méretének beállítása (a tartalmazott elemek alapján)
        pack();

        // A dialógust a BookShopGUI-hoz képest rajzolja ki
        setLocationRelativeTo(gui.getWindow());

        // Dialogus megjelenítése
        setVisible(true);
    }

    private JPanel createSettingPanel() {
        JPanel settingPanel = new JPanel();

        // A panel elrendezése mátrix, 5 sor és 2 oszlop, a cellák egyforma méretûek
        settingPanel.setLayout(new GridLayout(5,2));

        // Az 1. sorban egy név címke és egy szövegmezõ lesz
        settingPanel.add(new JLabel(Labels.user_name));
        settingPanel.add(this.nameTextfield);

        // A 2. sorban egy kor címke és egy spinner lesz
        settingPanel.add(new JLabel(Labels.user_eha));
        settingPanel.add(this.ehaTextfield);
        
     // A 2. sorban egy kor címke és egy spinner lesz
        settingPanel.add(new JLabel(Labels.user_room));
        settingPanel.add(this.roomTextfield);

        // A 4. sorban lesz a kedvezményezettség
        settingPanel.add(new JLabel(Labels.user_kb));
        settingPanel.add(kbCheck);

        // A 5. sorban lesz a végzettség
        settingPanel.add(new JLabel(Labels.user_admin));
        settingPanel.add(adminCheck);

        return settingPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        // A panel elrendezése folytonos, középre igazítva
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Hozzáadjuk az ok gombot, és figyelünk rá
        buttonPanel.add(okButton);
        okButton.addActionListener(this);

        // Hozzáadjuk a cancel gombot, és figyelünk rá
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(this);

        return buttonPanel;
    }

    private JPanel createDialogPanel(JPanel settingPanel, JPanel buttonPanel) {
        JPanel dialogPanel = new JPanel();

        // A panel elrendezése BorderLayout
        dialogPanel.setLayout(new BorderLayout());

        // Középen lesz a settingPanel
        dialogPanel.add(settingPanel, BorderLayout.CENTER);

        // Alul pedig a gombok
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        return dialogPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (okButton == e.getSource()) {
            // Ha az OK gombot nyomták meg, akkor megpróbáljuk felvenni az
            // ügyfelet

            if (nameTextfield.getText().isEmpty()) {
                // Ha nem adtak meg nevet, akkor egy hibaüzenetet írunk ki egy
                // error dialogra (JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.customer_name_is_required,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // létrehozzuk a customert
            User user = new User();

            user.setName(nameTextfield.getText());
            user.setEha(ehaTextfield.getText());
            user.setRoom(Integer.parseInt(roomTextfield.getText()));
            user.setKb(kbCheck.isSelected());
            user.setAdmin(kbCheck.isSelected());

            if (!gui.getController().addUser(user)) {
                // Ha az addCustomer false-t ad vissza akkor egy hibaüzenetet
                // írunk ki egy error dialogra(JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.customer_exists,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Ha az addCustomer true-t ad vissza akkor bezárjuk a dialógust
                setVisible(false);
            }
        } else if (cancelButton == e.getSource()) {
            // cancel esetén egyszerûen bezárjuk az ablakot
            setVisible(false);
        }
    }

}

