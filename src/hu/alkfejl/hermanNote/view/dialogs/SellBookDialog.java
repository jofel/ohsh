package hu.alkfejl.hermanNote.view.dialogs;

import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.view.BookShopGUI;
import hu.alkfejl.hermanNote.view.Labels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Az oszt�ly az �j v�s�rl�s r�gz�t�s�n�l megjelen� dial�gus.
 */
public class SellBookDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 2072911996817368496L;

    /**
     * �rt�k, mely {@link Book} v�laszt�s�nak hi�ny�t reprezent�lja.
     */
    private static final String BOOK_NONE_SELECTED = "";
    private static final String CUSTOMER_NONE_SELECTED = "";

    private BookShopGUI gui;

    private JButton okButton = new JButton(Labels.ok);
    private JButton cancelButton = new JButton(Labels.cancel);

    private Map<String, Book> bookMap = new HashMap<String, Book>();
    private Book selectedBook;

    private Map<String, Customer> customerMap = new HashMap<String, Customer>();
    private Customer selectedCustomer;

    public SellBookDialog(BookShopGUI gui, boolean modal) {
        super(gui.getWindow(), modal);

        this.gui = gui;

        prepareDataStructure();

        // A dial�gus c�m�nek be�ll�t�sa
        this.setTitle(Labels.sell_book);

        // A be�ll�t�sokat tartalmaz� panel gy�rt�sa
        Component settingPanel = createSettingPanel();

        // A gombokat tartalmaz� panel gy�rt�sa
        JPanel buttonPanel = createButtonPanel();

        // Az el�z� k�t panelt egy panelre rakjuk
        JPanel dialogPanel = createDialogPanel(settingPanel, buttonPanel); 

        // A dialogPanelt r�rakjuk a dial�gusra
        getContentPane().add(dialogPanel);

        setPreferredSize(new Dimension(600, 300));

        // A dial�gus megfelel� m�ret�nek be�ll�t�sa (a tartalmazott elemek alapj�n)
        pack();

        // A dial�gust a BookShopGUI-hoz k�pest rajzolja ki
        setLocationRelativeTo(gui.getWindow());

        // Dialogus megjelen�t�se
        setVisible(true);
    }

    /**
     * A controlleren kereszt�l megkapott adatok strukt�r�j�t (lista) a view
     * sz�ks�gleteinek megfelel�en �talak�tja.
     */
    private void prepareDataStructure() {
        List<Book> books = gui.getController().getBooks();

        for (Book book : books) {
            bookMap.put(book.getTitle(), book);
        }

        List<Customer> customers = gui.getController().getCustomers();

        for (Customer customer : customers) {
            customerMap.put(customer.getName(), customer);
        }
    }

    private Component createSettingPanel() {
        JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // a resizeWeigth property adja meg, hogy melyik panel mennyit kapjuk
        // a helyb�l �tm�retez�s eset�n, 0: egyik, 1: m�sik, 0.5: egyenl�en
        splitPanel.setResizeWeight(0.5);
        splitPanel.add(getLeftSettingsPanel());
        splitPanel.add(getRightSettingsPanel());

        return splitPanel;
    }

    private Container getLeftSettingsPanel(){
        // a k�nyvv�laszt� combo �sszerak�sa
        JComboBox<String> bookCombo = new JComboBox<String>();

        bookCombo.setPreferredSize(new Dimension(100, 20));

        // a combo elemeinek be�ll�t�sa
        bookCombo.addItem(BOOK_NONE_SELECTED);
        for (String bookTitle : bookMap.keySet()) {
            bookCombo.addItem(bookTitle);
        }

        JPanel bookComboPanel = new JPanel();
        bookComboPanel.add(bookCombo);

        // a k�nyv adatait mutat� panel
        JPanel dataPanel = new JPanel();

        dataPanel.setLayout(new GridLayout(0, 2));

        /* FIGYELEM!
         * A v�ltoz�k final-�k kell legyenek ahhoz, hogy el tudjuk �rni az
         * ItemListenerb�l.
         */

        dataPanel.add(new JLabel(Labels.author));
        final JLabel authorLabel = new JLabel();
        dataPanel.add(authorLabel);

        dataPanel.add(new JLabel(Labels.title));
        final JLabel titleLabel = new JLabel();
        dataPanel.add(titleLabel);

        dataPanel.add(new JLabel(Labels.year));
        final JLabel yearLabel = new JLabel();
        dataPanel.add(yearLabel);

        dataPanel.add(new JLabel(Labels.category));
        final JLabel categoryLabel = new JLabel();
        dataPanel.add(categoryLabel);

        dataPanel.add(new JLabel(Labels.price));
        final JLabel priceLabel = new JLabel();
        dataPanel.add(priceLabel);

        dataPanel.add(new JLabel(Labels.piece));
        final JLabel pieceLabel = new JLabel();
        dataPanel.add(pieceLabel);

        dataPanel.add(new JLabel(Labels.ancient));
        final JLabel ancientLabel = new JLabel();
        dataPanel.add(ancientLabel);

        bookCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // egy elem kiv�laszt�sakor 2 event keletkezik, egyik a r�gi
                // elem kijel�lts�g�nek megsz�n�se, m�sik a �j elem kijel�l�se
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedBookTitle = (String)e.getItem();

                    if (selectedBookTitle.equals(BOOK_NONE_SELECTED)) {
                        authorLabel.setText("");
                        titleLabel.setText("");
                        yearLabel.setText("");
                        categoryLabel.setText("");
                        priceLabel.setText("");
                        pieceLabel.setText("");
                        ancientLabel.setText("");
                    } else {
                        selectedBook = bookMap.get(selectedBookTitle);

                        authorLabel.setText(selectedBook.getAuthor());
                        titleLabel.setText(selectedBook.getTitle());
                        yearLabel.setText(Integer.toString(selectedBook.getYear()));
                        categoryLabel.setText(selectedBook.getCategory());
                        priceLabel.setText(Integer.toString(selectedBook.getPrice()));
                        pieceLabel.setText(Integer.toString(selectedBook.getPiece()));
                        ancientLabel.setText(Boolean.toString(selectedBook.isAncient()));
                    }
                }
            }
        });

        // a bal oldali settings panel �ssze�ll�t�sa (combo alatti r�szletek)
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());

        panel.add(bookComboPanel, BorderLayout.NORTH);
        panel.add(dataPanel, BorderLayout.CENTER);

        return panel;
    }

    private Container getRightSettingsPanel(){
        // a v�s�rl�v�laszt� combo �sszerak�sa
        JComboBox<String> customerCombo = new JComboBox<String>();

        customerCombo.setPreferredSize(new Dimension(100, 20));

        // a combo elemeinek be�ll�t�sa
        customerCombo.addItem(CUSTOMER_NONE_SELECTED);
        for (String customerName : customerMap.keySet()) {
            customerCombo.addItem(customerName);
        }

        JPanel customerComboPanel = new JPanel();
        customerComboPanel.add(customerCombo);

        // a v�s�rl� adatait mutat� panel
        JPanel dataPanel = new JPanel();

        dataPanel.setLayout(new GridLayout(0, 2));

        dataPanel.add(new JLabel(Labels.name));
        final JLabel nameLabel = new JLabel();
        dataPanel.add(nameLabel);

        dataPanel.add(new JLabel(Labels.age));
        final JLabel ageLabel = new JLabel();
        dataPanel.add(ageLabel);

        dataPanel.add(new JLabel(Labels.female));
        final JLabel genderLabel = new JLabel();
        dataPanel.add(genderLabel);

        dataPanel.add(new JLabel(Labels.qualification));
        final JLabel qualificationLabel = new JLabel();
        dataPanel.add(qualificationLabel);

        customerCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedCustomerName = (String)e.getItem();

                    if (selectedCustomerName.equals(CUSTOMER_NONE_SELECTED)) {
                        qualificationLabel.setText("");
                        nameLabel.setText("");
                        genderLabel.setText("");
                        ageLabel.setText("");
                    } else {
                        selectedCustomer = customerMap.get(selectedCustomerName);

                        qualificationLabel.setText(selectedCustomer.getQualification());
                        nameLabel.setText(selectedCustomer.getName());
                        genderLabel.setText(Boolean.toString(selectedCustomer.isFemale()));
                        ageLabel.setText(Integer.toString(selectedCustomer.getAge()));
                    }
                }
            }
        });

        // a jobb oldali settings panel �ssze�ll�t�sa (combo alatti r�szletek)
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());

        panel.add(customerComboPanel, BorderLayout.NORTH);
        panel.add(dataPanel, BorderLayout.CENTER);

        return panel;
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

    private JPanel createDialogPanel(Component settingPanel, JPanel buttonPanel) {
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
            // Ha az OK gombot nyomt�k meg, akkor megpr�b�ljuk felvenni a
            // k�nyvet

            if (selectedBook == null || selectedCustomer == null) {
                // Ha nem adtak meg c�met, akkor egy hiba�zenetet �runk ki egy
                // error dialogra (JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.purchase_choose_book_and_customer,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Purchase purchase = new Purchase();

            purchase.setBook(selectedBook);
            purchase.setCustomer(selectedCustomer);

            if (!gui.getController().addPurchase(purchase)) {
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.purchase_failed,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
            } else {
                setVisible(false);
            }
        } else if (cancelButton == e.getSource()) {
            // cancel eset�n egyszer�en bez�rjuk az ablakot
            setVisible(false);
        }
    }

}
