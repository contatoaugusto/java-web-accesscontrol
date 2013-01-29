package cobra.controleacesso.web.dto;


public class UsuarioDto {
	private int id_usuario;
	private boolean fg_ativo;
	private String nm_usuario;
	private String ds_senha;
	
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public boolean isFg_ativo() {
		return fg_ativo;
	}
	public void setFg_ativo(boolean fg_ativo) {
		this.fg_ativo = fg_ativo;
	}
	public String getNm_usuario() {
		return nm_usuario;
	}
	public void setNm_usuario(String nm_usuario) {
		this.nm_usuario = nm_usuario;
	}
	public String getDs_senha() {
		return ds_senha;
	}
	public void setDs_senha(String ds_senha) {
		this.ds_senha = ds_senha;
	}
	
}
