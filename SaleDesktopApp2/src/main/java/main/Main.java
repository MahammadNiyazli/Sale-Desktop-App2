/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dao.inter.OnHandGoodsDaoInter;
import dao.inter.SoldGoodsDaoInter;
import entity.OnHandGoods;
import entity.SoldGoods;
import java.awt.CardLayout;
import java.awt.Font;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.SingleSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author User
 */
public class Main extends javax.swing.JFrame {


     CardLayout cardLayout;
     OnHandGoodsDaoInter onHandGoodsDao = Context.instanceOnHandGoods();
     List<OnHandGoods> listOnHandGoods;
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     SoldGoodsDaoInter soldGoodsDao = Context.instanceSoldGoods();
     List<SoldGoods> listSoldGoods;
     boolean booleanForSilAndYenile = false;
    public Main() {
        initComponents();

        cardLayout = (CardLayout)(pnlCards.getLayout());
        fillMövcudMallarTable();
        cbVahidSatish.setSelectedItem(null);
        cbVahidSatilmishMallar.setSelectedItem(null);
        cbVahid.setSelectedItem(null);
    }
    
    public void fillComboBoxSatilanMalinAdi(){
        if(cbSatılanMalınAdı.getItemCount()>0){
          cbSatılanMalınAdı.removeAllItems();
        }
        
        for (OnHandGoods good : listOnHandGoods) {
            cbSatılanMalınAdı.addItem(good);
        }
        AutoCompleteDecorator.decorate(cbSatılanMalınAdı);
        
        cbSatılanMalınAdı.setSelectedItem(null);
        cbSatılanMalınAdı.setSelectedItem(null);
        
    }
    
    public void methodForSilAndYenile(){
        booleanForSilAndYenile = true;
    }
    
    public void fillComboBoxPnlMovcudMallar(){
        if(cbPnlMovcudMallar.getItemCount()>0){
          cbPnlMovcudMallar.removeAllItems();
        }
        
        for (OnHandGoods good : listOnHandGoods) {
            cbPnlMovcudMallar.addItem(good);
        }
        AutoCompleteDecorator.decorate(cbPnlMovcudMallar);
        
        cbPnlMovcudMallar.setSelectedItem(null);
        cbPnlMovcudMallar.setSelectedItem(null);
    }
    
        public void fillComboBoxPnlSatilmisMallar(){
        if(cbSatılmisMalınAdı.getItemCount()>0){
          cbSatılmisMalınAdı.removeAllItems();
        }
        
        for (OnHandGoods good : listOnHandGoods) {
            cbSatılmisMalınAdı.addItem(good);
        }
        AutoCompleteDecorator.decorate(cbSatılmisMalınAdı);
        
        cbSatılmisMalınAdı.setSelectedItem(null);
        cbSatılmisMalınAdı.setSelectedItem(null);
    }
    
    
    
    public void fillMövcudMallarTable(){
        listOnHandGoods = onHandGoodsDao.getAllOnHandGoods();
        Vector<Vector> rows = new Vector<>();
        
        for (OnHandGoods good : listOnHandGoods) {
            Vector row = new Vector();
            row.add(good.getName());
            row.add(good.getPrice());
            row.add(good.getCount()+"      "+good.getVahid());
            
            rows.add(row);
        }
        

        Vector<String> columns = new Vector<String>();
        columns.add("Malın adı");
        columns.add("Malın qiyməti / Azn");
        columns.add("Malın miqdarı");        
        DefaultTableModel model = new DefaultTableModel(rows,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
            
        };
        tblMövcudMallar.setModel(model);
        tableHeader();
        fillComboBoxSatilanMalinAdi();
        fillComboBoxPnlMovcudMallar();
        fillComboBoxPnlSatilmisMallar();
        fillSatlanMallarTable();
        fillMebleg();
        fillOldMebleg();
    }
    
    public void resetpnlMovcudMallar(){
        txtMövcudMalınAdı.setText("");
        txtMövcudMalınQiyməti.setText("");
        txtMövcudMalınSayı.setText("");
        cbPnlMovcudMallar.setSelectedItem(null);
        cbPnlMovcudMallar.setSelectedItem(null);
    }
    
    public void fillMebleg(){
        double mebleg = 0;
        if(!listSoldGoods.isEmpty()){
        for(SoldGoods good : listSoldGoods) {
            mebleg+=good.getPrice()*good.getCount();
        }
        txtMəbləğ.setText(mebleg+" Azn");
        }else{
            txtMəbləğ.setText("0.0 Azn");
        }
    }
    
    public void fillOldMebleg(){
        double mebleg = 0;
        if(listOldSoldGoods!=null){
        for(SoldGoods good : listOldSoldGoods) {
            mebleg+=good.getPrice()*good.getCount();
        }
        txtOldMəbləğ.setText(mebleg+" Azn");
        }else{
            txtOldMəbləğ.setText("0.0 Azn");
        }
    }
    
    public void axtarilanElementiCedveldeGoster(OnHandGoods good){
        Vector<Vector> rows = new Vector<>();
        Vector row = new Vector();
        row.add(good.getName());
        row.add(good.getPrice());
        row.add(good.getCount()+"    "+good.getVahid());    
        rows.add(row);
        Vector<String> columns = new Vector<String>();
        columns.add("Malın adı");
        columns.add("Malın qiyməti / Azn");
        columns.add("Malın miqdarı");        
        DefaultTableModel model = new DefaultTableModel(rows,columns);
        tblMövcudMallar.setModel(model);
        tableHeader();
    }
  
    
     public void butunDataniYenile(){
         fillComboBoxPnlMovcudMallar();
         fillComboBoxSatilanMalinAdi();
         fillComboBoxPnlSatilmisMallar();
         fillSatlanMallarTable();
         fillMebleg();
         fillOldMebleg();
     }
    
    public void fillSatlanMallarTable(){
        
             Date date = new Date();
             String dateString = sdf.format(date);
             listSoldGoods =  soldGoodsDao.getSoldGoodsBySalesDate(dateString);
             Vector<Vector> rows = new Vector<>();
        
             for (SoldGoods good : listSoldGoods) {
              Vector row = new Vector();
              row.add(good.getGood());
              row.add(good.getPrice());
              row.add(good.getCount()+"     "+good.getVahid());
              row.add(good.getSalesDate());
            
              rows.add(row);
             }
        
        Vector<String> columns = new Vector<String>();
        columns.add("Malın adı");
        columns.add("Malın qiyməti / Azn");
        columns.add("Malın miqdarı");
        columns.add("Malın Satış Tarixi");
        DefaultTableModel model = new DefaultTableModel(rows,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
            
        };
        tblBugunSatilanMallar.setModel(model);
             
         } 
    
    
       List<SoldGoods> listOldSoldGoods;
       public void filanTarixdeSatilanMallarTable(){
           String dateString = txtTarix.getText();
           listOldSoldGoods =  soldGoodsDao.getSoldGoodsBySalesDate(dateString);
           Vector<Vector> rows = new Vector<>();
        
             for (SoldGoods good : listOldSoldGoods) {
              Vector row = new Vector();
              row.add(good.getGood());
              row.add(good.getPrice());
              row.add(good.getCount()+"     "+good.getVahid());
              row.add(good.getSalesDate());
            
              rows.add(row);
             }
        

        Vector<String> columns = new Vector<String>();
        columns.add("Malın adı");
        columns.add("Malın qiyməti / Azn");
        columns.add("Malın miqdarı");
        columns.add("Malın Satış Tarixi");
        DefaultTableModel model = new DefaultTableModel(rows,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
            
        };
        tblSatilmisMallar.setModel(model);
        fillOldMebleg();
           
       }
    
    
    public void tableHeader(){
        JTableHeader tHead = tblMövcudMallar.getTableHeader();
        JTableHeader tHeadBugunSatilan = tblBugunSatilanMallar.getTableHeader();
        JTableHeader tHeadSatilmis = tblSatilmisMallar.getTableHeader();
        tHead.setFont(new Font("Tahoma",Font.BOLD,16));
        tHeadBugunSatilan.setFont(new Font("Tahoma",Font.BOLD,16));
        tHeadSatilmis.setFont(new Font("Tahoma",Font.BOLD,16));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        btnsPanel = new javax.swing.JPanel();
        btnMövcudMallar = new javax.swing.JButton();
        btnSatış = new javax.swing.JButton();
        btnSatılanMallar = new javax.swing.JButton();
        lblSaleApp = new javax.swing.JLabel();
        lblCopyRight = new javax.swing.JLabel();
        pnlCards = new javax.swing.JPanel();
        pnlMövcudMallar = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMövcudMallar = new javax.swing.JTable();
        lblMövcudMal = new javax.swing.JLabel();
        lbllMövcudMalınQiyməti = new javax.swing.JLabel();
        txtMövcudMalınQiyməti = new javax.swing.JTextField();
        lblMövcudMalınSayı = new javax.swing.JLabel();
        txtMövcudMalınSayı = new javax.swing.JTextField();
        btnYeniMalƏlavəEt = new javax.swing.JButton();
        btnMalıSil = new javax.swing.JButton();
        btnMalıYenile = new javax.swing.JButton();
        txtMövcudMalınAdı = new javax.swing.JTextField();
        btnMovcudMallarReset = new javax.swing.JButton();
        cbPnlMovcudMallar = new javax.swing.JComboBox<>();
        btnMovcudMallarAxtar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cbVahid = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        pnlSatış = new javax.swing.JPanel();
        txtSatılanMalınQiyməti = new javax.swing.JTextField();
        txtSatılanMalınSayı = new javax.swing.JTextField();
        btnYeniMalSat = new javax.swing.JButton();
        btnSatılanMalıSil = new javax.swing.JButton();
        btnSatılanMalıYenile = new javax.swing.JButton();
        lblSatılanMalınSayı = new javax.swing.JLabel();
        lbllSatılanMalınQiyməti = new javax.swing.JLabel();
        lblSatılanMalınAdı = new javax.swing.JLabel();
        cbSatılanMalınAdı = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBugunSatilanMallar = new javax.swing.JTable();
        btnSatisReset = new javax.swing.JButton();
        txtMəbləğ = new javax.swing.JTextField();
        lblMəbləğ = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbVahidSatish = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        pnlSatılanMallar = new javax.swing.JPanel();
        btnEvvelceSatılanMalıSil = new javax.swing.JButton();
        btnEvvelceSatılanMalıYenile = new javax.swing.JButton();
        txtTarix = new javax.swing.JTextField();
        lblTarixiDaxilEdin = new javax.swing.JLabel();
        btnGoster = new javax.swing.JButton();
        lblSatılmisMalınAdı = new javax.swing.JLabel();
        cbSatılmisMalınAdı = new javax.swing.JComboBox<>();
        lbllSatılmisMalınQiyməti = new javax.swing.JLabel();
        txtSatılmisMalınQiyməti = new javax.swing.JTextField();
        lblSatılmisMalınSayı = new javax.swing.JLabel();
        txtSatılmisMalınSayı = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSatilmisMallar = new javax.swing.JTable();
        btnPnlSatilmisMallarReset = new javax.swing.JButton();
        lbOldlMəbləğ = new javax.swing.JLabel();
        txtOldMəbləğ = new javax.swing.JTextField();
        cbVahidSatilmishMallar = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 0, 153));
        setLocation(new java.awt.Point(300, 120));
        setPreferredSize(new java.awt.Dimension(1420, 790));

        jSplitPane1.setBackground(new java.awt.Color(153, 0, 153));
        jSplitPane1.setDividerSize(0);

        btnsPanel.setBackground(new java.awt.Color(153, 0, 153));
        btnsPanel.setMinimumSize(new java.awt.Dimension(350, 0));
        btnsPanel.setPreferredSize(new java.awt.Dimension(920, 637));

        btnMövcudMallar.setBackground(new java.awt.Color(153, 0, 153));
        btnMövcudMallar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMövcudMallar.setForeground(new java.awt.Color(255, 255, 255));
        btnMövcudMallar.setText("Satılan Mallar");
        btnMövcudMallar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(153, 0, 153))); // NOI18N
        btnMövcudMallar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMövcudMallarActionPerformed(evt);
            }
        });

        btnSatış.setBackground(new java.awt.Color(153, 0, 153));
        btnSatış.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSatış.setForeground(new java.awt.Color(255, 255, 255));
        btnSatış.setText("Mövcud Mallar");
        btnSatış.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(153, 0, 153))); // NOI18N
        btnSatış.setMaximumSize(new java.awt.Dimension(75, 31));
        btnSatış.setMinimumSize(new java.awt.Dimension(75, 31));
        btnSatış.setPreferredSize(new java.awt.Dimension(75, 31));
        btnSatış.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatışActionPerformed(evt);
            }
        });

        btnSatılanMallar.setBackground(new java.awt.Color(153, 0, 153));
        btnSatılanMallar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSatılanMallar.setForeground(new java.awt.Color(255, 255, 255));
        btnSatılanMallar.setText("Satış");
        btnSatılanMallar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(153, 0, 153))); // NOI18N
        btnSatılanMallar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatılanMallarActionPerformed(evt);
            }
        });

        lblSaleApp.setFont(new java.awt.Font("Papyrus", 0, 50)); // NOI18N
        lblSaleApp.setForeground(new java.awt.Color(255, 255, 255));
        lblSaleApp.setText("SaleApp");

        lblCopyRight.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N
        lblCopyRight.setForeground(new java.awt.Color(255, 255, 255));
        lblCopyRight.setText(" Copyright © 2020 Developed by Mahammad Niyazli ");

        javax.swing.GroupLayout btnsPanelLayout = new javax.swing.GroupLayout(btnsPanel);
        btnsPanel.setLayout(btnsPanelLayout);
        btnsPanelLayout.setHorizontalGroup(
            btnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnsPanelLayout.createSequentialGroup()
                .addGroup(btnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(btnsPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSaleApp))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, btnsPanelLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(btnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSatılanMallar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMövcudMallar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSatış, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(57, 57, 57))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblCopyRight)
                .addContainerGap())
        );
        btnsPanelLayout.setVerticalGroup(
            btnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblSaleApp)
                .addGap(50, 50, 50)
                .addComponent(btnSatış, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addGap(79, 79, 79)
                .addComponent(btnSatılanMallar, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addGap(99, 99, 99)
                .addComponent(btnMövcudMallar, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addGap(116, 116, 116)
                .addComponent(lblCopyRight)
                .addGap(27, 27, 27))
        );

        jSplitPane1.setLeftComponent(btnsPanel);

        pnlCards.setLayout(new java.awt.CardLayout());

        pnlMövcudMallar.setBackground(new java.awt.Color(153, 153, 0));
        pnlMövcudMallar.setForeground(new java.awt.Color(255, 255, 255));

        tblMövcudMallar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblMövcudMallar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblMövcudMallar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblMövcudMallar.setRowHeight(28);
        tblMövcudMallar.setRowMargin(3);
        tblMövcudMallar.setSelectionBackground(new java.awt.Color(0, 153, 204));
        jScrollPane1.setViewportView(tblMövcudMallar);

        lblMövcudMal.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblMövcudMal.setForeground(new java.awt.Color(255, 255, 255));
        lblMövcudMal.setText("Adı :");

        lbllMövcudMalınQiyməti.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbllMövcudMalınQiyməti.setForeground(new java.awt.Color(255, 255, 255));
        lbllMövcudMalınQiyməti.setText("Qiyməti :");

        txtMövcudMalınQiyməti.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        lblMövcudMalınSayı.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblMövcudMalınSayı.setForeground(new java.awt.Color(255, 255, 255));
        lblMövcudMalınSayı.setText("Miqdarı :");

        txtMövcudMalınSayı.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        btnYeniMalƏlavəEt.setBackground(new java.awt.Color(0, 153, 153));
        btnYeniMalƏlavəEt.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnYeniMalƏlavəEt.setForeground(new java.awt.Color(255, 255, 255));
        btnYeniMalƏlavəEt.setText("Əlavə et");
        btnYeniMalƏlavəEt.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnYeniMalƏlavəEt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYeniMalƏlavəEtActionPerformed(evt);
            }
        });

        btnMalıSil.setBackground(new java.awt.Color(204, 0, 0));
        btnMalıSil.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnMalıSil.setForeground(new java.awt.Color(255, 255, 255));
        btnMalıSil.setText("Sil");
        btnMalıSil.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnMalıSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMalıSilActionPerformed(evt);
            }
        });

        btnMalıYenile.setBackground(new java.awt.Color(102, 153, 0));
        btnMalıYenile.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnMalıYenile.setForeground(new java.awt.Color(255, 255, 255));
        btnMalıYenile.setText("Yenilə");
        btnMalıYenile.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnMalıYenile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMalıYenileActionPerformed(evt);
            }
        });

        txtMövcudMalınAdı.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        btnMovcudMallarReset.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnMovcudMallarReset.setText("reset");
        btnMovcudMallarReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovcudMallarResetActionPerformed(evt);
            }
        });

        cbPnlMovcudMallar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        btnMovcudMallarAxtar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnMovcudMallarAxtar.setText("Axtar");
        btnMovcudMallarAxtar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMovcudMallarAxtarMouseClicked(evt);
            }
        });
        btnMovcudMallarAxtar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovcudMallarAxtarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Seç :");

        cbVahid.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbVahid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ədəd", "kq", "metr" }));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Azn");

        javax.swing.GroupLayout pnlMövcudMallarLayout = new javax.swing.GroupLayout(pnlMövcudMallar);
        pnlMövcudMallar.setLayout(pnlMövcudMallarLayout);
        pnlMövcudMallarLayout.setHorizontalGroup(
            pnlMövcudMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMövcudMallarLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lblMövcudMal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMövcudMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMövcudMallarLayout.createSequentialGroup()
                        .addComponent(btnYeniMalƏlavəEt)
                        .addGap(38, 38, 38)
                        .addComponent(btnMalıSil, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtMövcudMalınAdı, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlMövcudMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlMövcudMallarLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnMalıYenile, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 617, Short.MAX_VALUE))
                    .addGroup(pnlMövcudMallarLayout.createSequentialGroup()
                        .addComponent(lbllMövcudMalınQiyməti)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMövcudMalınQiyməti, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(48, 48, 48)
                        .addComponent(lblMövcudMalınSayı)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMövcudMalınSayı, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbVahid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(223, 223, 223)))
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addGroup(pnlMövcudMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlMövcudMallarLayout.createSequentialGroup()
                        .addComponent(cbPnlMovcudMallar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMovcudMallarAxtar))
                    .addComponent(btnMovcudMallarReset))
                .addGap(246, 246, 246))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlMövcudMallarLayout.setVerticalGroup(
            pnlMövcudMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMövcudMallarLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlMövcudMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMövcudMal)
                    .addComponent(lbllMövcudMalınQiyməti)
                    .addComponent(txtMövcudMalınQiyməti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMövcudMalınSayı)
                    .addComponent(txtMövcudMalınSayı, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMövcudMalınAdı, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPnlMovcudMallar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMovcudMallarAxtar)
                    .addComponent(jLabel2)
                    .addComponent(cbVahid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(34, 34, 34)
                .addGroup(pnlMövcudMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnYeniMalƏlavəEt)
                    .addComponent(btnMalıSil)
                    .addComponent(btnMalıYenile)
                    .addComponent(btnMovcudMallarReset))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 878, Short.MAX_VALUE))
        );

        pnlCards.add(pnlMövcudMallar, "pnlCard1");

        pnlSatış.setBackground(new java.awt.Color(0, 153, 153));

        txtSatılanMalınQiyməti.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        txtSatılanMalınSayı.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        btnYeniMalSat.setBackground(new java.awt.Color(0, 153, 153));
        btnYeniMalSat.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnYeniMalSat.setForeground(new java.awt.Color(255, 255, 255));
        btnYeniMalSat.setText("Sat");
        btnYeniMalSat.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnYeniMalSat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYeniMalSatActionPerformed(evt);
            }
        });

        btnSatılanMalıSil.setBackground(new java.awt.Color(204, 0, 0));
        btnSatılanMalıSil.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnSatılanMalıSil.setForeground(new java.awt.Color(255, 255, 255));
        btnSatılanMalıSil.setText("Sil");
        btnSatılanMalıSil.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnSatılanMalıSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatılanMalıSilActionPerformed(evt);
            }
        });

        btnSatılanMalıYenile.setBackground(new java.awt.Color(102, 153, 0));
        btnSatılanMalıYenile.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnSatılanMalıYenile.setForeground(new java.awt.Color(255, 255, 255));
        btnSatılanMalıYenile.setText("Yenilə");
        btnSatılanMalıYenile.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnSatılanMalıYenile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatılanMalıYenileActionPerformed(evt);
            }
        });

        lblSatılanMalınSayı.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblSatılanMalınSayı.setForeground(new java.awt.Color(255, 255, 255));
        lblSatılanMalınSayı.setText("Miqdarı :");

        lbllSatılanMalınQiyməti.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbllSatılanMalınQiyməti.setForeground(new java.awt.Color(255, 255, 255));
        lbllSatılanMalınQiyməti.setText("Qiyməti :");

        lblSatılanMalınAdı.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblSatılanMalınAdı.setForeground(new java.awt.Color(255, 255, 255));
        lblSatılanMalınAdı.setText("Adı :");

        cbSatılanMalınAdı.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        tblBugunSatilanMallar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblBugunSatilanMallar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblBugunSatilanMallar.setRowHeight(28);
        tblBugunSatilanMallar.setRowMargin(3);
        jScrollPane2.setViewportView(tblBugunSatilanMallar);

        btnSatisReset.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnSatisReset.setText("reset");
        btnSatisReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatisResetActionPerformed(evt);
            }
        });

        txtMəbləğ.setEditable(false);
        txtMəbləğ.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lblMəbləğ.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblMəbləğ.setForeground(new java.awt.Color(255, 255, 255));
        lblMəbləğ.setText("Məbləğ :");

        jLabel1.setFont(new java.awt.Font("Papyrus", 0, 50)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SaleApp");

        cbVahidSatish.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbVahidSatish.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ədəd", "kq", "metr" }));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Azn");

        javax.swing.GroupLayout pnlSatışLayout = new javax.swing.GroupLayout(pnlSatış);
        pnlSatış.setLayout(pnlSatışLayout);
        pnlSatışLayout.setHorizontalGroup(
            pnlSatışLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(pnlSatışLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblSatılanMalınAdı)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSatışLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlSatışLayout.createSequentialGroup()
                        .addComponent(btnYeniMalSat, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnSatılanMalıSil, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnSatılanMalıYenile)
                        .addGap(143, 143, 143))
                    .addGroup(pnlSatışLayout.createSequentialGroup()
                        .addComponent(cbSatılanMalınAdı, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbllSatılanMalınQiyməti)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSatılanMalınQiyməti, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(40, 40, 40)))
                .addGroup(pnlSatışLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlSatışLayout.createSequentialGroup()
                        .addComponent(lblSatılanMalınSayı)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSatılanMalınSayı, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbVahidSatish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSatisReset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                .addComponent(lblMəbləğ)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMəbləğ, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(jLabel1)
                .addGap(112, 112, 112))
        );
        pnlSatışLayout.setVerticalGroup(
            pnlSatışLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSatışLayout.createSequentialGroup()
                .addGroup(pnlSatışLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSatışLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(pnlSatışLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(pnlSatışLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSatılanMalınSayı, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbllSatılanMalınQiyməti)
                            .addComponent(lblSatılanMalınSayı)
                            .addComponent(txtSatılanMalınQiyməti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSatılanMalınAdı)
                            .addComponent(cbSatılanMalınAdı, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbVahidSatish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGroup(pnlSatışLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSatışLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(pnlSatışLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMəbləğ, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMəbləğ, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlSatışLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(pnlSatışLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnYeniMalSat)
                                    .addComponent(btnSatılanMalıSil)
                                    .addComponent(btnSatılanMalıYenile)
                                    .addComponent(btnSatisReset))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlCards.add(pnlSatış, "pnlCard2");

        pnlSatılanMallar.setBackground(new java.awt.Color(51, 153, 0));

        btnEvvelceSatılanMalıSil.setBackground(new java.awt.Color(204, 0, 0));
        btnEvvelceSatılanMalıSil.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnEvvelceSatılanMalıSil.setForeground(new java.awt.Color(255, 255, 255));
        btnEvvelceSatılanMalıSil.setText("Sil");
        btnEvvelceSatılanMalıSil.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnEvvelceSatılanMalıSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEvvelceSatılanMalıSilActionPerformed(evt);
            }
        });

        btnEvvelceSatılanMalıYenile.setBackground(new java.awt.Color(102, 153, 0));
        btnEvvelceSatılanMalıYenile.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnEvvelceSatılanMalıYenile.setForeground(new java.awt.Color(255, 255, 255));
        btnEvvelceSatılanMalıYenile.setText("Yenilə");
        btnEvvelceSatılanMalıYenile.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnEvvelceSatılanMalıYenile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEvvelceSatılanMalıYenileActionPerformed(evt);
            }
        });

        txtTarix.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtTarix.setToolTipText("il-ay-gün");

        lblTarixiDaxilEdin.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblTarixiDaxilEdin.setForeground(new java.awt.Color(255, 255, 255));
        lblTarixiDaxilEdin.setText("Tarixi daxil edin :");

        btnGoster.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnGoster.setText("Göstər");
        btnGoster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGosterActionPerformed(evt);
            }
        });

        lblSatılmisMalınAdı.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblSatılmisMalınAdı.setForeground(new java.awt.Color(255, 255, 255));
        lblSatılmisMalınAdı.setText("Adı :");

        cbSatılmisMalınAdı.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        lbllSatılmisMalınQiyməti.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbllSatılmisMalınQiyməti.setForeground(new java.awt.Color(255, 255, 255));
        lbllSatılmisMalınQiyməti.setText("Qiyməti :");

        txtSatılmisMalınQiyməti.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        lblSatılmisMalınSayı.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblSatılmisMalınSayı.setForeground(new java.awt.Color(255, 255, 255));
        lblSatılmisMalınSayı.setText("Miqdarı :");

        txtSatılmisMalınSayı.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        tblSatilmisMallar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblSatilmisMallar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblSatilmisMallar.setRowHeight(28);
        tblSatilmisMallar.setRowMargin(3);
        jScrollPane4.setViewportView(tblSatilmisMallar);

        btnPnlSatilmisMallarReset.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnPnlSatilmisMallarReset.setText("reset");
        btnPnlSatilmisMallarReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPnlSatilmisMallarResetActionPerformed(evt);
            }
        });

        lbOldlMəbləğ.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbOldlMəbləğ.setForeground(new java.awt.Color(255, 255, 255));
        lbOldlMəbləğ.setText("Məbləğ :");

        txtOldMəbləğ.setEditable(false);
        txtOldMəbləğ.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        cbVahidSatilmishMallar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbVahidSatilmishMallar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ədəd", "kq", "metr" }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Azn");

        javax.swing.GroupLayout pnlSatılanMallarLayout = new javax.swing.GroupLayout(pnlSatılanMallar);
        pnlSatılanMallar.setLayout(pnlSatılanMallarLayout);
        pnlSatılanMallarLayout.setHorizontalGroup(
            pnlSatılanMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(pnlSatılanMallarLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblSatılmisMalınAdı)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSatılanMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSatılanMallarLayout.createSequentialGroup()
                        .addComponent(btnEvvelceSatılanMalıSil, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnEvvelceSatılanMalıYenile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlSatılanMallarLayout.createSequentialGroup()
                        .addComponent(cbSatılmisMalınAdı, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(lbllSatılmisMalınQiyməti)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSatılmisMalınQiyməti, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(55, 55, 55)
                        .addComponent(lblSatılmisMalınSayı)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSatılmisMalınSayı, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbVahidSatilmishMallar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)))
                .addComponent(lblTarixiDaxilEdin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTarix, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlSatılanMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGoster)
                    .addComponent(btnPnlSatilmisMallarReset, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(lbOldlMəbləğ)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOldMəbləğ, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        pnlSatılanMallarLayout.setVerticalGroup(
            pnlSatılanMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSatılanMallarLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(pnlSatılanMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSatılanMallarLayout.createSequentialGroup()
                        .addGroup(pnlSatılanMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSatılmisMalınAdı)
                            .addComponent(cbSatılmisMalınAdı, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(pnlSatılanMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEvvelceSatılanMalıSil)
                            .addComponent(btnEvvelceSatılanMalıYenile)))
                    .addGroup(pnlSatılanMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSatılmisMalınSayı, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSatılmisMalınSayı)
                        .addComponent(cbVahidSatilmishMallar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbllSatılmisMalınQiyməti)
                        .addComponent(txtSatılmisMalınQiyməti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(pnlSatılanMallarLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(pnlSatılanMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSatılanMallarLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(pnlSatılanMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtOldMəbləğ, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbOldlMəbləğ, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlSatılanMallarLayout.createSequentialGroup()
                                .addGroup(pnlSatılanMallarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTarix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTarixiDaxilEdin)
                                    .addComponent(btnGoster))
                                .addGap(29, 29, 29)
                                .addComponent(btnPnlSatilmisMallarReset)))))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlCards.add(pnlSatılanMallar, "pnlCard3");

        jSplitPane1.setRightComponent(pnlCards);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1905, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSatışActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatışActionPerformed
       cardLayout.show(pnlCards, "pnlCard1");
    }//GEN-LAST:event_btnSatışActionPerformed

    private void btnSatılanMallarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatılanMallarActionPerformed
       cardLayout.show(pnlCards, "pnlCard2");
    }//GEN-LAST:event_btnSatılanMallarActionPerformed

    private void btnMövcudMallarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMövcudMallarActionPerformed
       cardLayout.show(pnlCards, "pnlCard3");
    }//GEN-LAST:event_btnMövcudMallarActionPerformed

    private void btnYeniMalƏlavəEtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYeniMalƏlavəEtActionPerformed
       try{
        String name = txtMövcudMalınAdı.getText();
        double price = Double.parseDouble(txtMövcudMalınQiyməti.getText());
        double count = Double.parseDouble(txtMövcudMalınSayı.getText());
        String vahid = (String) cbVahid.getSelectedItem();
       
         if((name!=null && !name.trim().isEmpty()) && (vahid!=null)){
               OnHandGoods good = new OnHandGoods(0, name, price, count,vahid);
               onHandGoodsDao.addOnHandGoods(good);
         }
        else JOptionPane.showMessageDialog(this, "Siz mütləq malın adını və vahidi daxil etməlisiniz !");
        butunDataniYenile();
        fillMövcudMallarTable();
       }catch(NumberFormatException ex){
           JOptionPane.showMessageDialog(this, "Siz parametrləri düzgün daxil etməmisiniz !");
       }
       
       resetpnlMovcudMallar();
       cbVahid.setSelectedItem(null);
    }//GEN-LAST:event_btnYeniMalƏlavəEtActionPerformed

    private void btnMalıSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMalıSilActionPerformed
        boolean p = true;
        List<SoldGoods> listAllSoldGoods = soldGoodsDao.getAllSoldGoods();
        OnHandGoods goodCb = (OnHandGoods) cbPnlMovcudMallar.getSelectedItem();
        if(goodCb ==null || booleanForSilAndYenile==false){
            int index = tblMövcudMallar.getSelectedRow();
            if(index!=-1){
                OnHandGoods good = listOnHandGoods.get(index);
                for (SoldGoods soldGood : listAllSoldGoods) {
                if(soldGood.getGood().getId()==good.getId()){
                    p = false;
                    break;
                }else{
                    p=true;
                }
            }
            
           if(p==true) onHandGoodsDao.removeOnHandGoods(good.getId());
           else  JOptionPane.showMessageDialog(this, " Siz bu elementi silmek haqqiniz yoxdur , cunki bu elementin evvelce satisi olub !");
           butunDataniYenile();
           fillMövcudMallarTable();
            }else{
                 JOptionPane.showMessageDialog(this, " Siz elementi seçməyi unutdunuz !");
            }
        }
        else if(goodCb!=null && booleanForSilAndYenile==true){
            
            for (SoldGoods good : listAllSoldGoods) {
                if(good.getGood().getId()==goodCb.getId()){
                    p = false;
                    break;
                }else{
                    p=true;
                }
            }
            
           if(p==true) onHandGoodsDao.removeOnHandGoods(goodCb.getId());
           else  JOptionPane.showMessageDialog(this, " Siz bu elementi silmek haqqiniz yoxdur , cunki bu elementin evvelce satisi olub !");
           butunDataniYenile();
           fillMövcudMallarTable();
        }else{
            JOptionPane.showMessageDialog(this, " Siz elementi seçməyi unutdunuz !");
        }
         booleanForSilAndYenile = false;
         resetpnlMovcudMallar();
    }//GEN-LAST:event_btnMalıSilActionPerformed

    private void btnMalıYenileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMalıYenileActionPerformed
        OnHandGoods goodCb = (OnHandGoods) cbPnlMovcudMallar.getSelectedItem();
        
        try{
        Double price = null;
        Double count = null;
        
        
        String name = txtMövcudMalınAdı.getText();
        String qiymet = txtMövcudMalınQiyməti.getText();
        String say = txtMövcudMalınSayı.getText();
        String vahid = (String) cbVahid.getSelectedItem();
        if(qiymet!=null && !qiymet.trim().isEmpty()) price = Double.parseDouble(qiymet);
        if(say!=null && !say.trim().isEmpty()) count = Double.parseDouble(say);
        
        if(goodCb==null || booleanForSilAndYenile==false){
            int index = tblMövcudMallar.getSelectedRow();
            if(index!=-1){
                OnHandGoods good = listOnHandGoods.get(index);
                 if(name!=null && !name.trim().isEmpty()) good.setName(name);
                 if(qiymet!=null && !qiymet.trim().isEmpty()) good.setPrice(price);
                 if(say!=null && !say.trim().isEmpty()) good.setCount(count);
                 if(vahid!=null) good.setVahid(vahid);
                 onHandGoodsDao.updateOnHandGoods(good);
                 butunDataniYenile();
                 fillMövcudMallarTable();
            }
        }
        
        else if(goodCb!=null && booleanForSilAndYenile==true){
        if(name!=null && !name.trim().isEmpty()) goodCb.setName(name);
        if(qiymet!=null && !qiymet.trim().isEmpty()) goodCb.setPrice(price);
        if(say!=null && !say.trim().isEmpty()) goodCb.setCount(count);
        if(vahid!=null) goodCb.setVahid(vahid);
        onHandGoodsDao.updateOnHandGoods(goodCb);
        butunDataniYenile();
        axtarilanElementiCedveldeGoster(goodCb);
        cbPnlMovcudMallar.setSelectedItem(goodCb);
        }else{
            JOptionPane.showMessageDialog(this, "Siz elementi secmeyi unutdunuz");
        }
    }catch(NumberFormatException ex){
        JOptionPane.showMessageDialog(this, "Siz parametrləri düzgün daxil etməmisiniz !");
      }
        booleanForSilAndYenile = false;
        
    }//GEN-LAST:event_btnMalıYenileActionPerformed

    private void btnYeniMalSatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYeniMalSatActionPerformed
       try{
        Double price = null;
        Double count = null;
        Date date = new Date();
        String dateWithSdf = sdf.format(date);
        OnHandGoods good = (OnHandGoods) cbSatılanMalınAdı.getSelectedItem();
        
        String qiymet = txtSatılanMalınQiyməti.getText();
        String say = txtSatılanMalınSayı.getText();
        String vahid = (String) cbVahidSatish.getSelectedItem();
        if((qiymet!=null && !qiymet.trim().isEmpty()) && (say!=null && !say.trim().isEmpty()) && (vahid!=null)) {
        price = (Double.parseDouble(qiymet));
        count = (Double.parseDouble(say));
        double qaliq = good.getCount() - count;
        if(qaliq>=0){
        good.setCount(qaliq);
        onHandGoodsDao.updateOnHandGoods(good);
        SoldGoods soldGood = new SoldGoods(0, good, price, count, dateWithSdf,vahid);
        soldGoodsDao.addSoldGoods(soldGood);
        fillMövcudMallarTable();
        butunDataniYenile();
       txtSatılanMalınQiyməti.setText("");
       txtSatılanMalınSayı.setText("");
       cbVahidSatish.setSelectedItem(null);
       cbSatılanMalınAdı.setSelectedItem(null);
        
          }else{
            JOptionPane.showMessageDialog(this, "Bazada sizin qeyd etdiyiniz miqdarda "+good.getName()+" yoxdur !");
        }
        }else{
            JOptionPane.showMessageDialog(this, "Siz parametrləri düzgün daxil etməmisiniz !");
            }
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Siz parametrləri düzgün daxil etməmisiniz !");
        }

    }//GEN-LAST:event_btnYeniMalSatActionPerformed

    private void btnSatılanMalıSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatılanMalıSilActionPerformed
      int index = tblBugunSatilanMallar.getSelectedRow();
      if(index!=-1){  
       SoldGoods soldGood = listSoldGoods.get(index);
       soldGoodsDao.removeSoldGoods(soldGood.getId());
       OnHandGoods good = soldGood.getGood();
       double newCount = good.getCount() + soldGood.getCount();
       good.setCount(newCount);
       onHandGoodsDao.updateOnHandGoods(good);
       fillMövcudMallarTable();
       butunDataniYenile();
      }else{
          JOptionPane.showMessageDialog(this, "Siz cədvəldən siləcəyiniz elementi seçməyi unutdunuz !");
      }
    }//GEN-LAST:event_btnSatılanMalıSilActionPerformed

    private void btnSatılanMalıYenileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatılanMalıYenileActionPerformed
        int index = tblBugunSatilanMallar.getSelectedRow();
        SoldGoods soldGood = null;
        if(index>=0){
        try{
        Double price = null;
        Double count = null;
       
        soldGood = listSoldGoods.get(index);
        OnHandGoods goodOld = soldGood.getGood();
        Object object =  cbSatılanMalınAdı.getSelectedItem();
        if(object==null){
        String qiymet = txtSatılanMalınQiyməti.getText();
        String say = txtSatılanMalınSayı.getText();
        String vahid = (String) cbVahidSatish.getSelectedItem();
       
        if(vahid!=null){
            soldGood.setVahid(vahid);
        }
        
        if(qiymet!=null && !qiymet.trim().isEmpty()) {
        price = (Double.parseDouble(qiymet));
        soldGood.setPrice(price);
       }
        if(say!=null && !say.trim().isEmpty()) {
        count = (Double.parseDouble(say));
        goodOld.setCount(goodOld.getCount()+soldGood.getCount());
        double qaliq = goodOld.getCount() - count;
        if(qaliq>=0){
        goodOld.setCount(qaliq);
        onHandGoodsDao.updateOnHandGoods(goodOld);
        soldGood.setCount(count);
       }else{
            JOptionPane.showMessageDialog(this, "Bazada sizin qeyd etdiyiniz miqdarda "+goodOld.getName()+" yoxdur !");
           }
         }
        }else{
            OnHandGoods goodNew = (OnHandGoods) object;
            String qiymet = txtSatılanMalınQiyməti.getText();
            String say = txtSatılanMalınSayı.getText();
            String vahid = (String) cbVahidSatish.getSelectedItem();
            soldGood.setGood(goodNew);
            if(say!=null && !say.trim().isEmpty()){
            count = (Double.parseDouble(say));
            goodOld.setCount(goodOld.getCount()+soldGood.getCount());
            double qaliq = goodNew.getCount() - count;
            if(qaliq>=0){
                goodNew.setCount(qaliq);
                soldGood.setCount(count);
                onHandGoodsDao.updateOnHandGoods(goodOld);
                onHandGoodsDao.updateOnHandGoods(goodNew);
            }else{
                JOptionPane.showMessageDialog(this, "Bazada sizin qeyd etdiyiniz miqdarda "+goodNew.getName()+" yoxdur !");
            }
          }else{
             goodOld.setCount(goodOld.getCount()+soldGood.getCount());
             double qaliq = goodNew.getCount() - soldGood.getCount();
             if(qaliq>=0){
             goodNew.setCount(qaliq);
             onHandGoodsDao.updateOnHandGoods(goodOld);
             onHandGoodsDao.updateOnHandGoods(goodNew);
             }else{
                 JOptionPane.showMessageDialog(this, "Bazada sizin qeyd edilen miqdarda "+goodNew.getName()+" yoxdur !");
             }
        }
        
        if(qiymet!=null && !qiymet.trim().isEmpty()) {
        price = (Double.parseDouble(qiymet));
        soldGood.setPrice(price);
       }
        
        if(vahid!=null){
            soldGood.setVahid(vahid);
        }
        

        }
        
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Siz parametrləri düzgün daxil etməmisiniz !");
        }
        soldGoodsDao.updateSoldGoods(soldGood);
        txtSatılanMalınQiyməti.setText("");
        txtSatılanMalınSayı.setText("");
        }else{
             JOptionPane.showMessageDialog(this, "Siz cədvəldən setir seçməyi unutdunuz !");
        }
        fillMövcudMallarTable();
        butunDataniYenile();
        cbVahidSatish.setSelectedItem(null);
    }//GEN-LAST:event_btnSatılanMalıYenileActionPerformed

    private void btnSatisResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatisResetActionPerformed
      txtSatılanMalınQiyməti.setText("");
      txtSatılanMalınSayı.setText("");
      cbSatılanMalınAdı.setSelectedItem(null);
    }//GEN-LAST:event_btnSatisResetActionPerformed

    private void btnMovcudMallarResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovcudMallarResetActionPerformed
        txtMövcudMalınAdı.setText("");
        txtMövcudMalınQiyməti.setText("");
        txtMövcudMalınSayı.setText("");
        cbPnlMovcudMallar.setSelectedItem(null);
        fillMövcudMallarTable();
        booleanForSilAndYenile = false;
        resetpnlMovcudMallar();
        cbVahid.setSelectedItem(null);
    }//GEN-LAST:event_btnMovcudMallarResetActionPerformed

    private void btnMovcudMallarAxtarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovcudMallarAxtarActionPerformed
        OnHandGoods good = (OnHandGoods) cbPnlMovcudMallar.getSelectedItem();
        axtarilanElementiCedveldeGoster(good);
    }//GEN-LAST:event_btnMovcudMallarAxtarActionPerformed

    private void btnEvvelceSatılanMalıSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEvvelceSatılanMalıSilActionPerformed
      int index = tblSatilmisMallar.getSelectedRow();
      if(index!=-1){  
          
       SoldGoods soldGood = listOldSoldGoods.get(index);
       soldGoodsDao.removeSoldGoods(soldGood.getId());
       OnHandGoods good = soldGood.getGood();
       double newCount = good.getCount() + soldGood.getCount();
       good.setCount(newCount);
       onHandGoodsDao.updateOnHandGoods(good);
       fillMövcudMallarTable();
       butunDataniYenile();
       filanTarixdeSatilanMallarTable();

      }else{
          JOptionPane.showMessageDialog(this, "Siz cədvəldən siləcəyiniz elementi seçməyi unutdunuz !");
      }        
    }//GEN-LAST:event_btnEvvelceSatılanMalıSilActionPerformed

    private void btnEvvelceSatılanMalıYenileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEvvelceSatılanMalıYenileActionPerformed
        int index = tblSatilmisMallar.getSelectedRow();
        SoldGoods soldGood = null;
        if(index>=0){
        try{
        Double price = null;
        Double count = null;
       
        soldGood = listOldSoldGoods.get(index);
        OnHandGoods goodOld = soldGood.getGood();
        Object object =  cbSatılmisMalınAdı.getSelectedItem();
        if(object==null){
        String qiymet = txtSatılmisMalınQiyməti.getText();
        String say = txtSatılmisMalınSayı.getText();
        String vahid = (String) cbVahidSatilmishMallar.getSelectedItem();
       
        if(vahid!=null){
            soldGood.setVahid(vahid);
        }
        
        if(qiymet!=null && !qiymet.trim().isEmpty()) {
        price = (Double.parseDouble(qiymet));
        soldGood.setPrice(price);
        soldGoodsDao.updateSoldGoods(soldGood);
       }
        if(say!=null && !say.trim().isEmpty()) {
        count = (Double.parseDouble(say));
        goodOld.setCount(goodOld.getCount()+soldGood.getCount());
        double qaliq = goodOld.getCount() - count;
        if(qaliq>=0){
        goodOld.setCount(qaliq);
        onHandGoodsDao.updateOnHandGoods(goodOld);
        soldGood.setCount(count);
        soldGoodsDao.updateSoldGoods(soldGood);
       }else{
            JOptionPane.showMessageDialog(this, "Bazada sizin qeyd etdiyiniz miqdarda "+goodOld.getName()+" yoxdur !");
           }
         }
        }else{
            OnHandGoods goodNew = (OnHandGoods) object;
            String qiymet = txtSatılmisMalınQiyməti.getText();
            String say = txtSatılmisMalınSayı.getText();
            String vahid = (String) cbVahidSatilmishMallar.getSelectedItem();
            soldGood.setGood(goodNew);
            if(say!=null && !say.trim().isEmpty()){
            count = (Double.parseDouble(say));
            goodOld.setCount(goodOld.getCount()+soldGood.getCount());
            double qaliq = goodNew.getCount() - count;
            if(qaliq>=0){
                goodNew.setCount(qaliq);
                soldGood.setCount(count);
                onHandGoodsDao.updateOnHandGoods(goodOld);
                onHandGoodsDao.updateOnHandGoods(goodNew);
            }else{
                JOptionPane.showMessageDialog(this, "Bazada sizin qeyd etdiyiniz miqdarda "+goodNew.getName()+" yoxdur !");
            }
          }else{
             goodOld.setCount(goodOld.getCount()+soldGood.getCount());
             double qaliq = goodNew.getCount() - soldGood.getCount();
             if(qaliq>=0){
             goodNew.setCount(qaliq);
             onHandGoodsDao.updateOnHandGoods(goodOld);
             onHandGoodsDao.updateOnHandGoods(goodNew);
             }else{
                 JOptionPane.showMessageDialog(this, "Bazada sizin qeyd edilen miqdarda "+goodNew.getName()+" yoxdur !");
             }
        }
        
        if(qiymet!=null && !qiymet.trim().isEmpty()) {
        price = (Double.parseDouble(qiymet));
        soldGood.setPrice(price);
       }
        
        if(vahid!=null){
            soldGood.setVahid(vahid);
        }
        

        }
        
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Siz parametrləri düzgün daxil etməmisiniz !");
        }
        soldGoodsDao.updateSoldGoods(soldGood);
        txtSatılmisMalınQiyməti.setText("");
        txtSatılmisMalınSayı.setText("");
        }else{
             JOptionPane.showMessageDialog(this, "Siz cədvəldən setir seçməyi unutdunuz !");
        }
        fillMövcudMallarTable();
        butunDataniYenile(); 
        filanTarixdeSatilanMallarTable();
        cbVahidSatilmishMallar.setSelectedItem(null);
    }//GEN-LAST:event_btnEvvelceSatılanMalıYenileActionPerformed

    private void btnGosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGosterActionPerformed
       filanTarixdeSatilanMallarTable();
    }//GEN-LAST:event_btnGosterActionPerformed

    private void btnPnlSatilmisMallarResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPnlSatilmisMallarResetActionPerformed
       cbSatılmisMalınAdı.setSelectedItem(null);
       txtTarix.setText("");
       txtSatılmisMalınSayı.setText("");
       txtSatılmisMalınQiyməti.setText("");
    }//GEN-LAST:event_btnPnlSatilmisMallarResetActionPerformed

    private void btnMovcudMallarAxtarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMovcudMallarAxtarMouseClicked
       methodForSilAndYenile();
    }//GEN-LAST:event_btnMovcudMallarAxtarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEvvelceSatılanMalıSil;
    private javax.swing.JButton btnEvvelceSatılanMalıYenile;
    private javax.swing.JButton btnGoster;
    private javax.swing.JButton btnMalıSil;
    private javax.swing.JButton btnMalıYenile;
    private javax.swing.JButton btnMovcudMallarAxtar;
    private javax.swing.JButton btnMovcudMallarReset;
    private javax.swing.JButton btnMövcudMallar;
    private javax.swing.JButton btnPnlSatilmisMallarReset;
    private javax.swing.JButton btnSatisReset;
    private javax.swing.JButton btnSatılanMallar;
    private javax.swing.JButton btnSatılanMalıSil;
    private javax.swing.JButton btnSatılanMalıYenile;
    private javax.swing.JButton btnSatış;
    private javax.swing.JButton btnYeniMalSat;
    private javax.swing.JButton btnYeniMalƏlavəEt;
    private javax.swing.JPanel btnsPanel;
    private javax.swing.JComboBox<OnHandGoods> cbPnlMovcudMallar;
    private javax.swing.JComboBox<OnHandGoods> cbSatılanMalınAdı;
    private javax.swing.JComboBox<OnHandGoods> cbSatılmisMalınAdı;
    private javax.swing.JComboBox<String> cbVahid;
    private javax.swing.JComboBox<String> cbVahidSatilmishMallar;
    private javax.swing.JComboBox<String> cbVahidSatish;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lbOldlMəbləğ;
    private javax.swing.JLabel lblCopyRight;
    private javax.swing.JLabel lblMövcudMal;
    private javax.swing.JLabel lblMövcudMalınSayı;
    private javax.swing.JLabel lblMəbləğ;
    private javax.swing.JLabel lblSaleApp;
    private javax.swing.JLabel lblSatılanMalınAdı;
    private javax.swing.JLabel lblSatılanMalınSayı;
    private javax.swing.JLabel lblSatılmisMalınAdı;
    private javax.swing.JLabel lblSatılmisMalınSayı;
    private javax.swing.JLabel lblTarixiDaxilEdin;
    private javax.swing.JLabel lbllMövcudMalınQiyməti;
    private javax.swing.JLabel lbllSatılanMalınQiyməti;
    private javax.swing.JLabel lbllSatılmisMalınQiyməti;
    private javax.swing.JPanel pnlCards;
    private javax.swing.JPanel pnlMövcudMallar;
    private javax.swing.JPanel pnlSatılanMallar;
    private javax.swing.JPanel pnlSatış;
    private javax.swing.JTable tblBugunSatilanMallar;
    private javax.swing.JTable tblMövcudMallar;
    private javax.swing.JTable tblSatilmisMallar;
    private javax.swing.JTextField txtMövcudMalınAdı;
    private javax.swing.JTextField txtMövcudMalınQiyməti;
    private javax.swing.JTextField txtMövcudMalınSayı;
    private javax.swing.JTextField txtMəbləğ;
    private javax.swing.JTextField txtOldMəbləğ;
    private javax.swing.JTextField txtSatılanMalınQiyməti;
    private javax.swing.JTextField txtSatılanMalınSayı;
    private javax.swing.JTextField txtSatılmisMalınQiyməti;
    private javax.swing.JTextField txtSatılmisMalınSayı;
    private javax.swing.JTextField txtTarix;
    // End of variables declaration//GEN-END:variables
}
