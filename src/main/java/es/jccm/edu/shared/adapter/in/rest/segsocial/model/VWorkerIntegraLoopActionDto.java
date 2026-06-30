package es.jccm.edu.shared.adapter.in.rest.segsocial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Schema(name = "VWorkerIntegraLoopActionDto", description = "Descripcion para el modelo recepción de datos de alumnos por centro de la Seguridad Social")
public class VWorkerIntegraLoopActionDto implements Serializable {

    @Schema(name = "id")
    @JsonProperty("id")
    private String id;

    @Schema(name = "type")
    @JsonProperty("type")
    private String type;

    @Schema(name = "signStatus")
    @JsonProperty("sign_status")
    private String signStatus;

    @Schema(name = "error")
    @JsonProperty("error")
    private String error;

    @Schema(name = "idc")
    @JsonProperty("idc")
    private String idc;

    @Schema(name = "procedureFile")
    @JsonProperty("procedure_file")
    private String procedureFile;

    @Schema(name = "dataFile")
    @JsonProperty("data_file")
    private String dataFile;

    @Schema(name = "companyId")
    @JsonProperty("company_id")
    private String companyId;

    @Schema(name = "registerDate")
    @JsonProperty("register_date")
    @JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
    private String registerDate;

    @Schema(name = "dischargeDate")
    @JsonProperty("discharge_date")
    @JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
    private String dischargeDate;

    @Schema(name = "fpDualScholarship")
    @JsonProperty("fp_dua_scholarship")
    private String fpDualScholarship;

    @Schema(name = "erasmusFpDualScholarship")
    @JsonProperty("erasmus_fp_dual_scholarship")
    private String erasmusFpDualScholarship;

    @Schema(name = "dateReceiptData")
    @JsonProperty("date_receipt_data")
    @JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
    private String dateReceiptData;

    @Schema(name = "registeringError")
    @JsonProperty("registering_error")
    private String registeringError;


    @Schema(name = "daysPracticals")
    @JsonProperty("days_practicals")
    private String daysPracticals;

    @Schema(name = "contractType")
    @JsonProperty("contract_type")
    private String contractType;

    @Schema(name = "partTimeCoefficient")
    @JsonProperty("part_time_coefficient")
    private String partTimeCoefficient;

    @Schema(name = "name")
    @JsonProperty("name")
    private String name;

    @Schema(name = "workerNif")
    @JsonProperty("worker_nif")
    private String workerNif;

    @Schema(name = "gender")
    @JsonProperty("gender")
    private String gender;

    @Schema(name = "birthDate")
    @JsonProperty("birth_date")
    @JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
    private String birthDate;

    @Schema(name = "municipality")
    @JsonProperty("municipality")
    private String municipality;

    @Schema(name = "province")
    @JsonProperty("province")
    private String province;

    @Schema(name = "regime")
    @JsonProperty("regime")
    private String regime;

    @Schema(name = "affiliationNumber")
    @JsonProperty("affiliation_number")
    private String affiliationNumber;

    @Schema(name = "contributionAccount")
    @JsonProperty("contribution_account")
    private String contributionAccount;

    @Schema(name = "contributionGroup")
    @JsonProperty("contribution_group")
    private String contributionGroup;

    @Schema(name = "occupation")
    @JsonProperty("occupation")
    private String occupation;

    @Schema(name = "workerCollective")
    @JsonProperty("worker_collective")
    private String workerCollective;

    @Schema(name = "status")
    @JsonProperty("status")
    private String status;

    @Schema(name = "monthlyQuotation")
    @JsonProperty("monthly_quotation")
    private String monthlyQuotation;

    @Schema(name = "erasmus")
    @JsonProperty("erasmus")
    private String erasmus;

    @Schema(name = "companyCif")
    @JsonProperty("company_cif")
    private String companyCif;

    @Schema(name = "haveContributions")
    @JsonProperty("have_contribution")
    private String haveContributions;

}
