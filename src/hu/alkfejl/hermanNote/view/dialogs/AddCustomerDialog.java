package hu.alkfejl.hermanNote.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.view.HermanNoteGUI;
import hu.alkfejl.hermanNote.view.Labels;

/**
 * Az oszt�ly az �j �gyf�l felv�tel�n�l megjelen� dial�gus.
 */
public class AddCustomerDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 2072911996817368496L;

    private HermanNoteGUI gui;

    // A dial�gus azon vez�rl�it melyekre sz�ks�g lesz az esem�nykezel�s sor�n
    // oszt�lyv�ltoz�k�nt defini�ljuk
    private JTextField nameTextfield = new JTextField(10);
    private JSpinner ageSpinner = new JSpinner();
    private JRadioButton femaleRadio = new JRadioButton(Labels.female);
    private JRadioButton maleRadio = new JRadioButton(Labels.male);
    private JCheckBox granteeCheck = new JCheckBox();
    private JComboBox<String> qualificationCombo = new JComboBox<String>(Labels.qualifications);
    private JButton okButton = new JButton(Labels.ok);
    private JButton cancelButton = new JButton(Labels.cancel);

    public AddCustomerDialog(HermanNoteGUI gui, boolean modal) {
        super(gui.getWindow(), modal);
        this.gui = gui;

        // A dial�gus c�m�nek be�ll�t�sa
        this.setTitle(Labels.add_customer);

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

        // Az els� sorban egy n�v c�mke �s egy sz�vegmez� lesz
        settingPanel.add(new JLabel(Labels.name));
        settingPanel.add(this.nameTextfield);

        // A m�sodik sorban egy kor c�mke �s egy spinner lesz
        settingPanel.add(new JLabel(Labels.age));
        settingPanel.add(this.ageSpinner);

        // A harmadik sorban egy nem c�mke �s k�t radiobutton lesz, amit egy
        // �jabb panelen helyez�nk el (gender_panel)
        // A gender_panelre jobbra z�rtan egym�s ut�n pakolhatunk vez�rl�ket
        settingPanel.add(new JLabel(Labels.gender));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        genderPanel.add(femaleRadio);
        genderPanel.add(maleRadio);

        // Alap�rtelmez�sben a female van bekapcsolva
        femaleRadio.setSelected(true);

        // A female-male-b�l csak egy v�laszthat� ki ez�rt egy ButtonGroupba rakjuk �ket
        ButtonGroup group = new ButtonGroup();
        group.add(femaleRadio);
        group.add(maleRadio);

        // mivel a radio buttonnak alap�rtelm�zesben nagyobb a magass�ga mint a
        // textfieldnek ez�rt �t�ll�tjuk az y koordin�t�kat akkor�ra, mint a
        // textfieldek eset�ben
        // ellenkez� esetben a textfield-ek is ar�nytalanul magasabbak lesznek
        // mivel GridLayout-unk van, ahol minden cella egyforma m�ret�
        maleRadio.setPreferredSize(new Dimension(
                maleRadio.getPreferredSize().width,
                nameTextfield.getPreferredSize().height));
        femaleRadio.setPreferredSize(new Dimension(
                femaleRadio.getPreferredSize().width,
                nameTextfield.getPreferredSize().height));
        settingPanel.add(genderPanel);

        // A 4. sorban lesz a kedvezm�nyezetts�g
        settingPanel.add(new JLabel(Labels.grantee));
        settingPanel.add(granteeCheck);

        // A 5. sorban lesz a v�gzetts�g
        settingPanel.add(new JLabel(Labels.qualification));
        settingPanel.add(qualificationCombo);

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
                        Labels.student_name_is_required,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // l�trehozzuk a customert
            Customer customer = new Customer();

            customer.setName(nameTextfield.getText());
            customer.setAge((Integer)ageSpinner.getValue());
            customer.setFemale(femaleRadio.isSelected());
            customer.setGrantee(granteeCheck.isSelected());
            customer.setQualification(qualificationCombo.getSelectedItem().toString());

            if (!gui.getController().addCustomer(customer)) {
                // Ha az addCustomer false-t ad vissza akkor egy hiba�zenetet
                // �runk ki egy error dialogra(JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.student_exists,
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
