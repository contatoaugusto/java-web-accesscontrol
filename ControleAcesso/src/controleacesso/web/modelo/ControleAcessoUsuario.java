package controleacesso.web.modelo;

// Generated 24/12/2012 11:25:30 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Antonio Augusto
 */
@Entity
@Table(name = "tbusuario", schema = "dbo")
@SequenceGenerator(name = "id_usuario_seq_controleacesso", sequenceName = "dbo.id_usuario_seq")
@NamedQueries({
    @NamedQuery(name = "ControleAcessoUsuario.findAll", query = "SELECT u FROM ControleAcessoUsuario u"),
    @NamedQuery(name = "ControleAcessoUsuario.findByLogin", query = "Select u from ControleAcessoUsuario u where u.nmLogin = :nmLogin and u.fgAtivo = true")
})
public class ControleAcessoUsuario implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_usuario_seq_controleacesso")
	@Column(name = "id_usuario", unique = true, nullable = false)
	private int idUsuario;
	
//	@JoinColumn(name = "id_pessoa", referencedColumnName = "id_pessoa")  
//	@ManyToOne
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pessoa", referencedColumnName = "idPessoa")
	private ControleAcessoPessoa tbpessoa;
	
	@Column(name = "nm_login", nullable = false, length = 50)
	private String nmLogin;
	
	@Column(name = "ds_senha", nullable = false)
	private String dsSenha;
	
	
	@Column(name = "fg_ativo", nullable = false)
	private boolean fgAtivo;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbusuario_perfil", schema = "dbo", joinColumns = { @JoinColumn(name = "id_usuario", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_perfil", nullable = false, updatable = false) })
	private Set<ControleAcessoPerfil> tbperfils = new HashSet(0);
	
	//private Integer idPessoa;
	
	public ControleAcessoUsuario(int idUsuario, String nmLogin, String dsSenha,
			boolean fgAtivo) {
		this.idUsuario = idUsuario;
		this.nmLogin = nmLogin;
		this.dsSenha = dsSenha;
		this.fgAtivo = fgAtivo;
	}

	public ControleAcessoUsuario(int idUsuario, ControleAcessoPessoa tbpessoa, String nmLogin,
			String dsSenha, boolean fgAtivo, Set <ControleAcessoPerfil> tbperfils, Integer idPessoa) {
		this.idUsuario = idUsuario;
		this.tbpessoa = tbpessoa;
		this.nmLogin = nmLogin;
		this.dsSenha = dsSenha;
		this.fgAtivo = fgAtivo;
		this.tbperfils = tbperfils;
		//this.idPessoa = idPessoa;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public ControleAcessoPessoa getTbpessoa() {
		return this.tbpessoa;
	}

	public void setTbpessoa(ControleAcessoPessoa tbpessoa) {
		this.tbpessoa = tbpessoa;
	}

	public String getNmLogin() {
		return this.nmLogin;
	}

	public void setNmLogin(String nmLogin) {
		this.nmLogin = nmLogin;
	}

	public String getDsSenha() {
		return this.dsSenha;
	}

	public void setDsSenha(String dsSenha) {
		this.dsSenha = dsSenha;
	}

	public boolean isFgAtivo() {
		return this.fgAtivo;
	}

	public void setFgAtivo(boolean fgAtivo) {
		this.fgAtivo = fgAtivo;
	}

	public Set<ControleAcessoPerfil> getTbperfils() {
		return this.tbperfils;
	}

	public void setTbperfils(Set <ControleAcessoPerfil>tbperfils) {
		this.tbperfils = tbperfils;
	}
	
	public ControleAcessoUsuario() {
	}
	
	/**
	 * @return
	 */
//	@Column(name = "id_pessoa")
//	public Integer getIdPessoa() {
//		return this.idPessoa;
//	}
//
//	public void setIdPessoa(Integer idPessoa) {
//		this.idPessoa = idPessoa;
//	}

}
