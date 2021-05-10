/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.britoX.telas;

import java.sql.*;
import br.com.britoX.dal.ModuloConexao;
import javax.swing.JOptionPane;
// a linha abaixo importa recurcos da biblioteca rs2xml
import net.proteanit.sql.DbUtils;

/**
 *
 * @author ppn
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();

    }

    // metodo para adicionar clientes
    private void adicionar() {
        String sql = "insert into tbclientes (nomecli, endcli, fonecli, emailcli) values (?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jtfCliNome.getText());
            pst.setString(2, jtfCliEndereco.getText());
            pst.setString(3, jtfCliTelefone.getText());
            pst.setString(4, jtfCliEmail.getText());

            // validação dos campos obrigatorios
            if ((jtfCliNome.getText().isEmpty()) || (jtfCliEndereco.getText().isEmpty()) || (jtfCliTelefone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
                jtfCliNome.requestFocus();
            } else {
                int adicionado = pst.executeUpdate();
            // a linha abaixo atualiza a tabela usuarios com os dados do formulario
                // a extrutura abaixo é usada para confirmar a inserção dos dados na tabela
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso.:");
                    jtfCliNome.setText(null);
                    jtfCliEndereco.setText(null);
                    jtfCliTelefone.setText(null);
                    jtfCliEmail.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para pesquisar cliente no Banco de Dados
    private void pesquisar_cliente() {
        String sql = "select * from tbclientes where nomecli like ?";
        try {
            pst = conexao.prepareCall(sql);
            //passando o conteudo dea caixa de pesquisa para o "?"
            // atencao ao "%" que é a continuação da string sql
            pst.setString(1, jtfPesquisar.getText()+ "%");
            rs = pst.executeQuery();
            // a linha abaixo usa a biclioteca rs2.xml.jar para preencher a tabela da tela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // metodo para setar os campos do forms com o conteudo da tabela ao clicar
    public void setar_campos(){
        int setar = tblClientes.getSelectedRow();
        jtfIdCli.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        jtfCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        jtfCliEndereco.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        jtfCliTelefone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        jtfCliEmail.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
        
        // a linha abaixo desabilita o botao de adicionar para nao ocorrer duplicação quando
        // estiver editando um cadastro existente
        jbAdicionar.setEnabled(false);
    }
    
    
    private void alterar(){
        String sql = "update tbclientes set nomecli=?, endcli=?, fonecli=?, emailcli=? where idcli=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jtfCliNome.getText());
            pst.setString(2, jtfCliEndereco.getText());
            pst.setString(3, jtfCliTelefone.getText());
            pst.setString(4, jtfCliEmail.getText());
            pst.setString(5, jtfIdCli.getText());
            // validação dos campos obrigatorios
             if ((jtfCliNome.getText().isEmpty()) || (jtfCliEndereco.getText().isEmpty()) || (jtfCliTelefone.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Selecione um Cliente para editar.:");
            } else{
            int adicionado = pst.executeUpdate();
            // a linha abaixo atualiza a tabela clientes com os dados do formulario
            // a extrutura abaixo é usada para confirmar a inserção dos dados na tabela
            //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso.:");
                    jtfCliNome.setText(null);
                    jtfCliEndereco.setText(null);
                    jtfCliTelefone.setText(null);
                    jtfCliEmail.setText(null);
                    
                    // reabilitar o botao de adicionar
                    jbAdicionar.setEnabled(true);
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void remover(){
            if (jtfIdCli.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Nada para apagar");
                }
            else{
            
        
        // a estrutura abaixo confirma a exclusao de um cliente
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o cliente ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma==JOptionPane.YES_OPTION){
            String sql = "delete from tbclientes where idcli=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, jtfIdCli.getText());
                int apagado = pst.executeUpdate();
                
                if (apagado >0){
                    JOptionPane.showMessageDialog(null, "Removido com sucesso");
                    jtfCliNome.setText(null);
                    jtfCliEndereco.setText(null);
                    jtfCliEmail.setText(null);
                    jtfCliTelefone.setText(null);
                    jbAdicionar.setEnabled(true);
                    jtfPesquisar.setText(null);
                    pesquisar_cliente();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }}
    
    private void limpar_campos(){
         jtfCliNome.setText(null);
        jtfCliEndereco.setText(null);
        jtfCliTelefone.setText(null);
        jtfCliEmail.setText(null);
        jbAdicionar.setEnabled(true);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lCamposObrigatorios = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfCliNome = new javax.swing.JTextField();
        jtfCliEndereco = new javax.swing.JTextField();
        jtfCliTelefone = new javax.swing.JTextField();
        jtfCliEmail = new javax.swing.JTextField();
        jbAdicionar = new javax.swing.JButton();
        jbAlterar = new javax.swing.JButton();
        jbLimparCampos = new javax.swing.JButton();
        jtfPesquisar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtfIdCli = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jbExcluir = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setMinimumSize(new java.awt.Dimension(124, 34));
        setPreferredSize(new java.awt.Dimension(800, 521));

        lCamposObrigatorios.setText("* Campos Obrigatórios");

        jLabel1.setText("* Nome");

        jLabel2.setText("* Endereço");

        jLabel3.setText("* Telefone");

        jLabel4.setText("E-mail");

        jbAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/britoX/icones/create.png"))); // NOI18N
        jbAdicionar.setToolTipText("Adicionar");
        jbAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        jbAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarActionPerformed(evt);
            }
        });

        jbAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/britoX/icones/document-edit_114472 (1).png"))); // NOI18N
        jbAlterar.setToolTipText("Editar");
        jbAlterar.setPreferredSize(new java.awt.Dimension(80, 80));
        jbAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAlterarActionPerformed(evt);
            }
        });

        jbLimparCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/britoX/icones/icon+_borracha.png"))); // NOI18N
        jbLimparCampos.setToolTipText("Limpar os campos");
        jbLimparCampos.setPreferredSize(new java.awt.Dimension(80, 80));
        jbLimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparCamposActionPerformed(evt);
            }
        });

        jtfPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisarKeyReleased(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/britoX/icones/icon_pesquisar32x.png"))); // NOI18N

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel6.setText("Pesquisar Cliente");

        jLabel7.setText("ID Cliente");

        jtfIdCli.setEnabled(false);

        jLabel8.setText("INSERIR");

        jLabel9.setText("EDITAR");

        jLabel10.setText("EXCLUIR");

        jbExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/britoX/icones/delete.png"))); // NOI18N
        jbExcluir.setToolTipText("Excluir");
        jbExcluir.setPreferredSize(new java.awt.Dimension(80, 80));
        jbExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExcluirActionPerformed(evt);
            }
        });

        jLabel11.setText("LIMPAR CAMPOS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jtfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lCamposObrigatorios))
                    .addComponent(jScrollPane1))
                .addGap(114, 114, 114))
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfCliTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfIdCli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel8)
                                .addGap(86, 86, 86)
                                .addComponent(jLabel9)
                                .addGap(83, 83, 83)
                                .addComponent(jLabel10)))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(lCamposObrigatorios))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtfIdCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfCliTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtfCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)
                        .addComponent(jLabel10)))
                .addGap(20, 20, 20))
        );

        setBounds(0, 0, 800, 530);
    }// </editor-fold>//GEN-END:initComponents

    private void jbAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarActionPerformed
        // metodo para adicionar Cliente
        adicionar();
    }//GEN-LAST:event_jbAdicionarActionPerformed

    private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed
        // chamando o metodo alterar clientes
        alterar();
    }//GEN-LAST:event_jbAlterarActionPerformed

    private void jbLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparCamposActionPerformed
        // chamando metodo para limpar os campos
        limpar_campos();
    }//GEN-LAST:event_jbLimparCamposActionPerformed

    // o evento abaixo é do tipo "enquanto for digitando
    private void jtfPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisarKeyReleased
        // chamando metodo pesquisar cliente
        pesquisar_cliente();
    }//GEN-LAST:event_jtfPesquisarKeyReleased

    // Ao clicar na linha da tabela, preenche os campos da tabela da tela
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // chamando o metodo para setar os campos
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
        // chamando metodo para remover um registro
        remover();
    }//GEN-LAST:event_jbExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAdicionar;
    private javax.swing.JButton jbAlterar;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JButton jbLimparCampos;
    private javax.swing.JTextField jtfCliEmail;
    private javax.swing.JTextField jtfCliEndereco;
    private javax.swing.JTextField jtfCliNome;
    private javax.swing.JTextField jtfCliTelefone;
    private javax.swing.JTextField jtfIdCli;
    private javax.swing.JTextField jtfPesquisar;
    private javax.swing.JLabel lCamposObrigatorios;
    private javax.swing.JTable tblClientes;
    // End of variables declaration//GEN-END:variables
}
