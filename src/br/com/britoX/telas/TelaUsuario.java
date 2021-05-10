/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.britoX.telas;
import java.sql.*;
import br.com.britoX.dal.ModuloConexao;
import javax.swing.JOptionPane;

/**
 *
 * @author ppn Pedro Brito
 */
public class TelaUsuario extends javax.swing.JInternalFrame {
    // usando a variavel de conexao do DAL
    Connection conexao = null;
    // criando variaveis especiais para conexao com o banco de dados e servem
    // para preparar e executar as intruções SQL
    PreparedStatement pst = null;
    ResultSet rs = null;

    
    // * Criando o form TelaUsuario
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
        
    }
    // metodo para consultar um usuario
    private void consultar(){
        String sql = "select * from tbusuarios where iduser=?";
        // tratamento para caso não encontre o banco de dados ou dê alguma excessao
        
        
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jtfid.getText());
            
            
            rs = pst.executeQuery();
            if (rs.next()) {
                jtfNome.setText(rs.getString(2));
                jtfFone.setText(rs.getString(3));
                jtfLogin.setText(rs.getString(4));
                jtfSenha.setText(rs.getString(5));
                jcbPerfil.setSelectedItem(rs.getString(6));
                
              
            } else {
                if (jtfid.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Informe um ID para a busca");
                    jtfid.requestFocus();
                    jtfNome.setText(null);
                    jtfFone.setText(null);
                    jtfLogin.setText(null);
                    jtfSenha.setText(null);
                    jcbPerfil.setSelectedItem(null);

                }
                else{
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado");
                // as linhas abaixo limpam os campos do forms
                 jtfNome.setText(null);
                 jtfFone.setText(null);
                 jtfLogin.setText(null);
                 jtfSenha.setText(null);
                 jcbPerfil.setSelectedItem(null);
                 
            }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // metodo para adicionar um usuario novo
    private void adicionar(){
        String sql = "insert into tbusuarios (iduser, usuario, fone, login, senha, perfil) values (?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jtfid.getText());
            pst.setString(2, jtfNome.getText());
            pst.setString(3, jtfFone.getText());
            pst.setString(5, jtfSenha.getText());
            pst.setString(4, jtfLogin.getText());
            pst.setString(6, jcbPerfil.getSelectedItem().toString()); // o combobox precisa ser convertido para string
            
            // validação dos campos obrigatorios
             if ((jtfid.getText().isEmpty()) || (jtfNome.getText().isEmpty()) || (jtfLogin.getText().isEmpty()) || (jtfSenha.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else{
            int adicionado = pst.executeUpdate();
            // a linha abaixo atualiza a tabela usuarios com os dados do formulario
            // a extrutura abaixo é usada para confirmar a inserção dos dados na tabela
            //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso.:");
                    jtfid.setText(null);
                    jtfNome.setText(null);
                    jtfFone.setText(null);
                    jtfLogin.setText(null);
                    jtfSenha.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // metod para alterar dados do usuario
    private void alterar(){
        String sql = "update tbusuarios set usuario=?, fone=?, login=?, senha=?, perfil=? where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jtfNome.getText());
            pst.setString(2, jtfFone.getText());
            pst.setString(3, jtfLogin.getText());
            pst.setString(4, jtfSenha.getText());
            pst.setString(5, jcbPerfil.getSelectedItem().toString());  
            pst.setString(6, jtfid.getText());
            
            // validação dos campos obrigatorios
             if ((jtfid.getText().isEmpty()) || (jtfNome.getText().isEmpty()) || (jtfLogin.getText().isEmpty()) || (jtfSenha.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else{
            int adicionado = pst.executeUpdate();
            // a linha abaixo atualiza a tabela usuarios com os dados do formulario
            // a extrutura abaixo é usada para confirmar a inserção dos dados na tabela
            //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do usuário alterados com sucesso.:");
                    jtfid.setText(null);
                    jtfNome.setText(null);
                    jtfFone.setText(null);
                    jtfLogin.setText(null);
                    jtfSenha.setText(null);
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
   
    // metodo resposnavel pela exclusão de usuarios
    private void remover(){
        // a estrutura abaixo confirma a exclusao do usuario
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o usuário ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma==JOptionPane.YES_OPTION){
            String sql = "delete from tbusuarios where iduser=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, jtfid.getText());
                int apagado = pst.executeUpdate();
                if (apagado >0){
                    JOptionPane.showMessageDialog(null, "Removido com sucesso");
                    jtfid.setText(null);
                    jtfNome.setText(null);
                    jtfFone.setText(null);
                    jtfLogin.setText(null);
                    jtfSenha.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lId = new javax.swing.JLabel();
        lNome = new javax.swing.JLabel();
        lLogin = new javax.swing.JLabel();
        lSenha = new javax.swing.JLabel();
        lPerfil = new javax.swing.JLabel();
        jtfLogin = new javax.swing.JTextField();
        jtfNome = new javax.swing.JTextField();
        jtfid = new javax.swing.JTextField();
        jtfSenha = new javax.swing.JTextField();
        jcbPerfil = new javax.swing.JComboBox();
        lTelefone = new javax.swing.JLabel();
        jtfFone = new javax.swing.JTextField();
        jbAdicionar = new javax.swing.JButton();
        jbConsultar = new javax.swing.JButton();
        jbAlterar = new javax.swing.JButton();
        jbExcluir = new javax.swing.JButton();
        lCamposObrigatorios = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(800, 521));

        lId.setText("* ID");

        lNome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lNome.setText("* Nome");

        lLogin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lLogin.setText("* Login");

        lSenha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lSenha.setText("* Senha");

        lPerfil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lPerfil.setText("* Perfil");

        jtfLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfLoginActionPerformed(evt);
            }
        });

        jtfid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfidActionPerformed(evt);
            }
        });

        jcbPerfil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "admin", "user" }));

        lTelefone.setText("Telefone");

        jbAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/britoX/icones/create.png"))); // NOI18N
        jbAdicionar.setToolTipText("Adicionar");
        jbAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        jbAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarActionPerformed(evt);
            }
        });

        jbConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/britoX/icones/read.png"))); // NOI18N
        jbConsultar.setToolTipText("Consultar");
        jbConsultar.setPreferredSize(new java.awt.Dimension(80, 80));
        jbConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbConsultarActionPerformed(evt);
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

        jbExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/britoX/icones/delete.png"))); // NOI18N
        jbExcluir.setToolTipText("Excluir");
        jbExcluir.setPreferredSize(new java.awt.Dimension(80, 80));
        jbExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExcluirActionPerformed(evt);
            }
        });

        lCamposObrigatorios.setText("* Campos Obrigatórios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lSenha)
                            .addComponent(lNome)
                            .addComponent(lId)
                            .addComponent(lTelefone))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jtfNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jtfFone, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jtfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lLogin)
                                        .addComponent(lPerfil))
                                    .addGap(30, 30, 30)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jtfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jcbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtfid, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 337, Short.MAX_VALUE)
                                .addComponent(lCamposObrigatorios))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jbConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)))
                .addGap(104, 104, 104))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lCamposObrigatorios)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfFone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lTelefone)
                    .addComponent(jtfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        setBounds(0, 0, 800, 528);
    }// </editor-fold>//GEN-END:initComponents

    private void jtfLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfLoginActionPerformed

    private void jtfidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfidActionPerformed

    private void jbConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConsultarActionPerformed
        // chamando o metodo consultar
        consultar();
    }//GEN-LAST:event_jbConsultarActionPerformed

    private void jbAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarActionPerformed
        // chamando o metodo adicionar
        adicionar();
    }//GEN-LAST:event_jbAdicionarActionPerformed

    private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed
        // chamando o metodo alterar
        alterar();
    }//GEN-LAST:event_jbAlterarActionPerformed

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
        // chamando o metodo remover
        remover();
    }//GEN-LAST:event_jbExcluirActionPerformed

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAdicionar;
    private javax.swing.JButton jbAlterar;
    private javax.swing.JButton jbConsultar;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JComboBox jcbPerfil;
    private javax.swing.JTextField jtfFone;
    private javax.swing.JTextField jtfLogin;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField jtfSenha;
    private javax.swing.JTextField jtfid;
    private javax.swing.JLabel lCamposObrigatorios;
    private javax.swing.JLabel lId;
    private javax.swing.JLabel lLogin;
    private javax.swing.JLabel lNome;
    private javax.swing.JLabel lPerfil;
    private javax.swing.JLabel lSenha;
    private javax.swing.JLabel lTelefone;
    // End of variables declaration//GEN-END:variables
}
