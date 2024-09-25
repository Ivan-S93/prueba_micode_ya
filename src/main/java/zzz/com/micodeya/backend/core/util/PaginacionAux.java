package zzz.com.micodeya.backend.core.util;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class PaginacionAux {

	private boolean all; // todos
	private int page; // pagina
	private int limit; // limitePorPagina
	private String orderBy; // campo asc,campo desc
	private List<Map<String, Object>> list; // resultadoList
	private int total;
	private String extra;
	private String atributos;
	private List<String> detalles;
	private List<FilterAux> filterList;
	private Boolean lineal;

	// "all": false, "page":1, "limit": 10, "orderBy": "nombre asc"
	public PaginacionAux(String orderBy) {
		this.all = true;
		this.page = 1;
		this.limit = 0;
		this.orderBy = orderBy;
	}

	public PaginacionAux(String orderBy,List<FilterAux> filterList) {
		this.all = true;
		this.page = 1;
		this.limit = 0;
		this.orderBy = orderBy;
		this.filterList = filterList;
	}

	public PaginacionAuxResponse getParaResponse() {

		return new PaginacionAuxResponse(this);
	}

	@Setter
	@Getter
	class PaginacionAuxResponse {

		private boolean all; // todos
		private int page; // pagina
		private int limit; // limitePorPagina
		private List<Map<String, Object>> list; // resultadoList
		private int total;
		private String extra;

		public PaginacionAuxResponse(PaginacionAux pagAux) {
			all = pagAux.isAll();
			page = pagAux.getPage();
			limit = pagAux.getLimit();
			list = pagAux.getList();
			total = pagAux.getTotal();
			extra = pagAux.getExtra();
		}

	}

}
