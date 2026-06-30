package es.jccm.edu.shared.application.domain.segsocial.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class WorkersIntegraLoopAction  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "sign_status")
    private String signStatus;

    @Column(name = "error")
    private String error;

    @Column(name = "idc")
    private String idc;

    @Column(name = "procedure_file")
    private String procedureFile;

    @Column(name = "data_file")
    private String dataFile;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "register_date")
    private String registerDate;

    @Column(name = "discharge_date")
    private String dischargeDate;

    @Column(name = "fp_dual_scholarship")
    private String fpDualScholarship;

    @Column(name = "erasmus_fp_duañ_scholarship")
    private String erasmusFpDualScholarship;

    @Column(name = "date_receipt_data")
    private String dateReceiptData;

    @Column(name = "registering_error")
    private String registeringError;

    @Column(name = "days_practicals")
    private String daysPracticals;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "part_time_coefficient")
    private String partTimeCoefficient;

    @Column(name = "name")
    private String name;

    @Column(name = "worker_nif")
    private String workerNif;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "municipality")
    private String municipality;

    @Column(name = "province")
    private String province;

    @Column(name = "regime")
    private String regime;

    @Column(name = "affiliation_number")
    private String affiliationNumber;

    @Column(name = "contribution_account")
    private String contributionAccount;

    @Column(name = "contribution_group")
    private String contributionGroup;

    @Column(name = "occupation")
    private String contribution;

    @Column(name = "worker_collective")
    private String workerCollective;

    @Column(name = "status")
    private String status;

    @Column(name = "monthly_quotation")
    private String monthlyQuotation;

    @Column(name = "erasmus")
    private String erasmus;

    @Column(name = "company_cif")
    private String companyCif;

    @Column(name = "have_contributions")
    private String haveContributions;

}
