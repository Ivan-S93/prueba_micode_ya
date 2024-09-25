package zzz.com.micodeya.backend.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import zzz.com.micodeya.backend.core.exception.KwfAuthException;
import zzz.com.micodeya.backend.core.exception.KwfCoreException;
import zzz.com.micodeya.backend.core.exception.ValidacionException;

public class JeResponse {

	private Map<String, Object> retornoMap;
	private Map<String, Object> resultadoMap;
	private List<String> errorValList;
	private List<String> mensajeExitoList;
	private String mensajeErrorFatal;
	private String errorForLog;
	private boolean warning;

	public JeResponse(String mensajeExito, String mensajeErrorFatal) {

		this.retornoMap = new HashMap<String, Object>();
		this.resultadoMap = new HashMap<String, Object>();
		this.errorValList = new LinkedList<String>();
		
		this.mensajeExitoList = new LinkedList<String>();
		this.mensajeExitoList.add(mensajeExito);
		this.mensajeErrorFatal = mensajeErrorFatal;
		this.warning = false;
	}

	public JeResponse(Map<String, Object> retornoMap, Map<String, Object> resultadoMap, List<String> errorValList) {
		super();
		this.retornoMap = retornoMap;
		this.resultadoMap = resultadoMap;
		this.errorValList = errorValList;
	}

	public Map<String, Object> getRetornoMap() {
		return retornoMap;
	}

	// private void setRetornoMap(Map<String, Object> retornoMap) {
	// 	this.retornoMap = retornoMap;
	// }

	private Map<String, Object> getResultadoMap() {
		return resultadoMap;
	}

	// private void setResultadoMap(Map<String, Object> resultadoMap) {
	// 	this.resultadoMap = resultadoMap;
	// }

	public void putResultado(String key, Object value) {
		this.resultadoMap.put(key, value);
	}
	
	public void putResultadoListar(PaginacionAux paginacionAux) {
		// this.retornoMap.put("result", paginacionAux);
		this.retornoMap.put("result", paginacionAux.getParaResponse());
	}

	public List<String> getErrorValList() {
		return errorValList;
	}

	// private void setErrorValList(List<String> errorValList) {
	// 	this.errorValList = errorValList;
	// }

	public List<String> getMensajeExitoList() {
		return mensajeExitoList;
	}

	public void addMensajeExito(String mensajeExito) {
		this.mensajeExitoList.add(mensajeExito);
	}

	public void replaceMensajeExito(String mensajeExito) {
		this.mensajeExitoList = new LinkedList<String>();
		this.mensajeExitoList.add(mensajeExito);
	}

    public void setMensajeErrorFatal(String mensajeErrorFatal) {
		this.mensajeErrorFatal = mensajeErrorFatal;
	}

	public String getErrorForLog() {
		return this.errorForLog;
	}

	public void setErrorForLog(String errorForLog) {
		this.errorForLog = errorForLog;
	}

	public boolean isWarning() {
		return this.warning;
	}

	public void setWarning(boolean warning) {
		this.warning = warning;
	}


	public void addErrorValidacion(String errorValidacion) {
		this.errorValList.add(errorValidacion);
	}

	public void addErrorValidacion(List<String> errorValidacion) {
		this.errorValList.addAll(errorValidacion);
	}

	public boolean sinErrorValidacion() {
		return this.errorValList.size() == 0;
	}

	public void prepararRetornoMap() {

		if (this.errorValList.size() == 0) {

			this.retornoMap.put("ok", true);
			this.retornoMap.put("msg", mensajeExitoList);
			
			if(!this.retornoMap.containsKey("result"))
				this.retornoMap.put("result", resultadoMap);
			
		} else {
			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", errorValList);
			
		}

	}

	public void prepararRetornoErrorMap(Exception e) {

		if (e instanceof jakarta.validation.ConstraintViolationException) {

			final Iterator<ConstraintViolation<?>> iterator = ((ConstraintViolationException) e)
					.getConstraintViolations().iterator();
			while (iterator.hasNext()) {

				ConstraintViolation<?> violation = iterator.next();
				// String errorValidacion = String.format("[%s] %s, valor:'%s'.", violation.getPropertyPath(),
				// 		violation.getMessage(), violation.getInvalidValue());
				String errorValidacion = String.format("%s, valor:'%s'.",
						violation.getMessage(), violation.getInvalidValue());
				this.errorValList.add(errorValidacion);
				

			}

			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", errorValList);
			this.errorForLog = "Error de Validación: " + this.errorValList;


		} else if (e.getCause() != null && e.getCause().getCause() != null
				&& e.getCause().getCause() instanceof jakarta.validation.ConstraintViolationException) {

			final Iterator<ConstraintViolation<?>> iterator = ((ConstraintViolationException) e.getCause().getCause())
					.getConstraintViolations().iterator();

			while (iterator.hasNext()) {

				ConstraintViolation<?> violation = iterator.next();
				// String errorValidacion = String.format("[%s] %s, valor:'%s'", violation.getPropertyPath(),
				// violation.getMessage(), violation.getInvalidValue());
				String errorValidacion = String.format("%s, valor:'%s'", 
						violation.getMessage(), violation.getInvalidValue());
				this.errorValList.add(errorValidacion);

			}

			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", errorValList);
			this.errorForLog = "Error de Validación: " + this.errorValList;

		} else if (e instanceof ValidacionException) {

			this.errorValList.add(e.getMessage());

			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", errorValList);
			this.errorForLog = "" + this.errorValList;

		} else if (e instanceof ValidacionException) {

			this.errorValList.add(e.getMessage());

			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", errorValList);
			this.errorForLog = "" + this.errorValList;

		} else if (e instanceof ValidacionException) {

			this.errorValList.add(e.getMessage());

			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", errorValList);
			this.errorForLog = "" + this.errorValList;

			if(((ValidacionException) e).isWarning()) this.warning=true;
		
		} else if (e instanceof KwfCoreException) {

			this.errorValList.add("[kwf]"+e.getMessage());

			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", errorValList);
			this.errorForLog = "" + this.errorValList;

			if(((KwfCoreException) e).isWarning()) this.warning=true;

		} 
		else if (e instanceof KwfAuthException) {

			this.errorValList.add(""+e.getMessage());

			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg",errorValList);
			this.retornoMap.put("codigoEstado", ((KwfAuthException) e).getCodigoEstado());
			this.errorForLog = "" + this.errorValList;
  
			if(((KwfAuthException) e).isWarning()) this.warning=true;

		}else {

			String mensajeError = this.mensajeErrorFatal;
			long codSeg = System.nanoTime();
			mensajeError += JeBoot.esExcepcionKwf(e) + " CodSeg: " + codSeg;
			this.retornoMap.put("codSeg", codSeg);

			// log.fatal("<user>"+(userSession==null?request.getRemoteAddr():userSession.getEmpresaCoreCuentaUsuario())+"</user>"+
			// mensajeError,e);

			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", new String[]{mensajeError});
			this.errorForLog = mensajeError;

		}

	}

}
