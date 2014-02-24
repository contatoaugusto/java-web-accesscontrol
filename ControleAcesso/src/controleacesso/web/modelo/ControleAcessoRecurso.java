package controleacesso.web.modelo;


import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author Antonio Augusto
 */
@Entity
@Table(name = "tbrecurso", schema = "dbo")
@SequenceGenerator(name = "id_recurso_seq_controleacesso", sequenceName = "dbo.id_recurso_seq")
@NamedQueries({
    @NamedQuery(name = "ControleAcessoRecurso.findAll", query = "SELECT r from ControleAcessoRecurso r ORDER BY r.nmRecurso"),
    @NamedQuery(name = "ControleAcessoRecurso.findByAtivo", query = "SELECT r FROM ControleAcessoRecurso r where r.fgAtivo = :fgAtivo ORDER BY r.nmRecurso")
})

public class ControleAcessoRecurso implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_recurso_seq_controleacesso")
	@Column(name = "id_recurso", unique = true, nullable = false)
	private int idRecurso;

	@Column(name = "nm_recurso", nullable = false,  length = 50)
	private String nmRecurso;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_inclusao", nullable = false, length = 13)
	private Date dtInclusao;
	
	@Column(name = "id_tiporecurso", nullable = false)
	private Integer idTiporecurso;
	//private Integer idRecursopai;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_recursopai", insertable = true, updatable = true, nullable = true)
	private ControleAcessoRecurso tbrecursoFilho;
	
	@Column(name = "ds_recurso", length = 8000)
	private String dsRecurso;
	
	@Column(name = "lk_link", nullable = false, length = 200)
	private String lkLink;
	
	@Column(name = "fg_ativo", nullable = false, columnDefinition = "boolean default true")
	private Boolean fgAtivo;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tb_recurso_perfil", schema = "dbo", joinColumns = { @JoinColumn(name = "id_recurso", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_perfil", nullable = false, updatable = false) })
	private Set <ControleAcessoPerfil>tbperfils = new HashSet(0);
	
	public ControleAcessoRecurso() {
	}

	public ControleAcessoRecurso(int idRecurso) {
		this.idRecurso = idRecurso;
	}

	public ControleAcessoRecurso(int idRecurso, String nmRecurso, Date dtInclusao,
			Integer idTiporecurso, Integer idRecursopai, String dsRecurso,
			String lkLink, Boolean fgAtivo, Set <ControleAcessoPerfil>tbperfils, ControleAcessoRecurso tbreucursoFilho) {
		this.idRecurso = idRecurso;
		this.nmRecurso = nmRecurso;
		this.dtInclusao = dtInclusao;
		this.idTiporecurso = idTiporecurso;
		//this.idRecursopai = idRecursopai;
		this.tbrecursoFilho = tbreucursoFilho;
		this.dsRecurso = dsRecurso;
		this.lkLink = lkLink;
		this.fgAtivo = fgAtivo;
		this.tbperfils = tbperfils;
	}

	public int getIdRecurso() {
		return this.idRecurso;
	}

	public void setIdRecurso(int idRecurso) {
		this.idRecurso = idRecurso;
	}

	public String getNmRecurso() {
		return this.nmRecurso;
	}

	public void setNmRecurso(String nmRecurso) {
		this.nmRecurso = nmRecurso;
	}

	public Date getDtInclusao() {
		return this.dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public Integer getIdTiporecurso() {
		return this.idTiporecurso;
	}

	public void setIdTiporecurso(Integer idTiporecurso) {
		this.idTiporecurso = idTiporecurso;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@Column(name = "id_recursopai", nullable = false)
//	public Integer getIdRecursopai() {
//		return this.idRecursopai;
//	}
//
//	public void setIdRecursopai(Integer idRecursopai) {
//		this.idRecursopai = idRecursopai;
//	}

	public ControleAcessoRecurso getTbrecursoFilho() {
		return this.tbrecursoFilho;
	}

	public void setTbrecursoFilho(ControleAcessoRecurso tbrecurso) {
		this.tbrecursoFilho = tbrecurso;
	}
	
	public String getDsRecurso() {
		return this.dsRecurso;
	}

	public void setDsRecurso(String dsRecurso) {
		this.dsRecurso = dsRecurso;
	}

	public String getLkLink() {
		return this.lkLink;
	}

	public void setLkLink(String lkLink) {
		this.lkLink = lkLink;
	}

	public Boolean getFgAtivo() {
		return this.fgAtivo;
	}

	public void setFgAtivo(Boolean fgAtivo) {
		this.fgAtivo = fgAtivo;
	}

	public Set <ControleAcessoPerfil>getTbperfils() {
		return this.tbperfils;
	}

	public void setTbperfils(Set <ControleAcessoPerfil>tbperfils) {
		this.tbperfils = tbperfils;
	}

}
