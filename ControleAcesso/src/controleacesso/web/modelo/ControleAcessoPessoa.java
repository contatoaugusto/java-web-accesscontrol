package controleacesso.web.modelo;

// Generated 24/12/2012 11:14:52 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author Antonio Augusto
 */
@Entity
@Table(name = "\"Pessoa\"", schema = "dbo")
@SequenceGenerator(name = "id_pessoa_seq_controleacesso", sequenceName = "dbo.id_pessoa_seq")
@NamedQueries({
    @NamedQuery(name = "ControleAcessoPessoa.findAll", query = "SELECT p FROM ControleAcessoPessoa p ORDER BY p.nmPessoa")})
public class ControleAcessoPessoa implements java.io.Serializable {

	@Id
	@Column(name = "idpessoa", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_pessoa_seq_controleacesso")
	private int idPessoa;
	
	@Column(name = "nmpessoa")
	private String nmPessoa;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbpessoa")
	private Set<ControleAcessoUsuario> tbusuarios = new HashSet(0);

	public ControleAcessoPessoa() {
	}

	public ControleAcessoPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}

	public ControleAcessoPessoa(int idPessoa, String nmPessoa, Set <ControleAcessoUsuario>tbusuarios) {
		this.idPessoa = idPessoa;
		this.nmPessoa = nmPessoa;
		this.tbusuarios = tbusuarios;
	}

	public int getIdPessoa() {
		return this.idPessoa;
	}

	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getNmPessoa() {
		return this.nmPessoa;
	}

	public void setNmPessoa(String nmPessoa) {
		this.nmPessoa = nmPessoa;
	}

	public Set <ControleAcessoUsuario>getTbusuarios() {
		return this.tbusuarios;
	}

	public void setTbusuarios(Set <ControleAcessoUsuario>tbusuarios) {
		this.tbusuarios = tbusuarios;
	}

	
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControleAcessoPessoa)) {
            return false;
        }
        ControleAcessoPessoa other = (ControleAcessoPessoa) object;
        if ((this.idPessoa == 0 && other.idPessoa != 0) || (this.idPessoa != 0 && this.idPessoa != other.idPessoa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "br.com.sgdq.app.entity.Pessoa[ idPessoa=" + idPessoa + " ]";
    	return nmPessoa;
    }
}
