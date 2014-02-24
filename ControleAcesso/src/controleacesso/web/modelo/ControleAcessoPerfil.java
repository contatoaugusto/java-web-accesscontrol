package controleacesso.web.modelo;

// Generated 18/12/2012 20:46:58 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Antonio Augusto
 */
@Entity
@Table(name = "tbperfil", schema="dbo")
@SequenceGenerator(name = "id_perfil_seq_controleacesso", sequenceName = "dbo.id_perfil_seq")
@NamedQueries({
    @NamedQuery(name = "ControleAcessoPerfil.findAll", query = "SELECT p FROM ControleAcessoPerfil p"),
    @NamedQuery(name = "ControleAcessoPerfil.findByUsuario", query = "SELECT p FROM ControleAcessoPerfil p INNER JOIN FETCH  p.tbusuarios usuario where usuario.idUsuario = :idUsuario")
})
public class ControleAcessoPerfil implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_perfil_seq_controleacesso")
	@Column(name = "id_perfil", unique = true, nullable = false)
	private int idPerfil;
	
	@Column(name = "ds_perfil", length = 50)
	private String dsPerfil;
	
	@Column(name = "fg_ativo")
	private Boolean fgAtivo;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "tbperfils")
	private Set <ControleAcessoRecurso> tbrecursos = new HashSet(0);
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "tbperfils")
	private Set <ControleAcessoUsuario>tbusuarios = new HashSet(0);

	public ControleAcessoPerfil() {
	}

	public ControleAcessoPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	public ControleAcessoPerfil(int idPerfil, String dsPerfil, Boolean fgAtivo,
			Set <ControleAcessoRecurso> tbrecursos, Set <ControleAcessoUsuario>tbusuarios) {
		this.idPerfil = idPerfil;
		this.dsPerfil = dsPerfil;
		this.fgAtivo = fgAtivo;
		this.tbrecursos = tbrecursos;
		this.tbusuarios = tbusuarios;
	}

	public int getIdPerfil() {
		return this.idPerfil;
	}

	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getDsPerfil() {
		return this.dsPerfil;
	}

	public void setDsPerfil(String dsPerfil) {
		this.dsPerfil = dsPerfil;
	}

	public Boolean getFgAtivo() {
		return this.fgAtivo;
	}

	public void setFgAtivo(Boolean fgAtivo) {
		this.fgAtivo = fgAtivo;
	}

	public Set<ControleAcessoRecurso> getTbrecursos() {
		return this.tbrecursos;
	}

	public void setTbrecursos(Set <ControleAcessoRecurso>tbrecursos) {
		this.tbrecursos = tbrecursos;
	}

	public Set <ControleAcessoUsuario> getTbusuarios() {
		return this.tbusuarios;
	}

	public void setTbusuarios(Set <ControleAcessoUsuario>tbusuarios) {
		this.tbusuarios = tbusuarios;
	}

}
