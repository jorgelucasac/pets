/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.Conexao;
import entidades.Endereco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jodi
 */
public class EnderecoDao {

    private Conexao conexao;
    private int idGerado;

    public EnderecoDao() {

        this.conexao = Conexao.getInstancia();
        this.idGerado = 0;
    }

    private Endereco buscarEndereco(Endereco idEndereco) {
        String sqlEndereco = "select * from endereco WHERE id=?;";
        this.conexao.preparar(sqlEndereco);

        try {
            this.conexao.getPs().setInt(1, idEndereco.getId());
            ResultSet resultado = conexao.executeQuery();

            if (resultado != null && resultado.next()) {
                idEndereco.setBairro(resultado.getString("bairro"));
                idEndereco.setLogradouro(resultado.getString("logradouro"));
                idEndereco.setCep(resultado.getString("cep"));
                idEndereco.setComplemento(resultado.getString("complemento"));
                idEndereco.setNumero(resultado.getInt("numero"));
                idEndereco.setIdCidade(new Cidade(resultado.getInt("idCidade")));

            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, "erro ao verificar telefone", ex);
        }

        return idEndereco;

    }

    private void inserirEndereco(Endereco endereco) {

        String SqlEndereco = "INSERT INTO endereco (logradouro, bairro, `idCidade`, cep, complemento, numero)"
                + "	VALUES (?, ?, ?, ?, ?, ?);";
        this.conexao.prepararAI(SqlEndereco);

        try {
            this.conexao.getPs().setString(1, endereco.getLogradouro());
            this.conexao.getPs().setString(2, endereco.getBairro());
            this.conexao.getPs().setInt(3, endereco.getIdCidade().getId());
            this.conexao.getPs().setString(4, endereco.getCep());
            this.conexao.getPs().setString(5, endereco.getComplemento());
            this.conexao.getPs().setInt(6, endereco.getNumero());
            if (this.conexao.executeUpdate()) {

                this.idGerado = this.conexao.getAutoIncrement();
                System.out.println("Inserido!");

            } else {
                System.out.println("Faiou ao cadastrar endereço!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateEndereco(Endereco endereco) {

        String SqlEndereco = "UPDATE  endereco SET logradouro=?, bairro=?, `idCidade`=?, cep=?, complemento=?, numero=? WHERE id=?;";

        this.conexao.preparar(SqlEndereco);

        try {
            this.conexao.getPs().setString(1, endereco.getLogradouro());
            this.conexao.getPs().setString(2, endereco.getBairro());
            this.conexao.getPs().setInt(3, endereco.getIdCidade().getId());
            this.conexao.getPs().setString(4, endereco.getCep());
            this.conexao.getPs().setString(5, endereco.getComplemento());
            this.conexao.getPs().setInt(6, endereco.getNumero());
            this.conexao.getPs().setInt(6, endereco.getId());

            if (this.conexao.executeUpdate()) {
                System.out.println("Inserido!");

            } else {
                System.out.println("Faiou ao cadastrar endereço!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletarCidade(Endereco endereco) {
        String query = "delete FROM endereco WHERE id=?;";

        this.conexao.preparar(query);
        try {
            this.conexao.getPs().setInt(1, endereco.getId());

            if (this.conexao.executeUpdate()) {
                System.out.println("deletado!");

            } else {
                System.out.println("Faiou!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getIdGerado() {
        return idGerado;
    }
}
