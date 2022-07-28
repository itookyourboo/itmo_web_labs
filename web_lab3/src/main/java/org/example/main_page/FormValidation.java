package org.example.main_page;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class FormValidation {

    @ManagedBean
    @SessionScoped
    @FacesValidator("xValidator")
    public static class XValidator implements Validator<Double> {

        private Double x;

        public Double getX() {
            return x;
        }

        public void setX(Double x) {
            this.x = x;
        }

        public XValidator() {
        }

        @Override
        public void validate(FacesContext facesContext, UIComponent uiComponent, Double o) throws ValidatorException {
            if (o == null) {
                FacesMessage msg = new FacesMessage("Введите X");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
            try {
                if (o >= 4 || o <= -4) {
                    FacesMessage msg = new FacesMessage("X не входит в ОДЗ: (-4, 4)");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }
            } catch (NumberFormatException exception) {
                FacesMessage msg = new FacesMessage("X не является числом");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    @ManagedBean
    @SessionScoped
    @FacesValidator("yValidator")
    public static class YValidator implements Validator<Double> {

        private Double y;

        public Double getY() {
            return y;
        }

        public void setY(Double y) {
            this.y = y;
        }

        public YValidator() {
        }

        @Override
        public void validate(FacesContext facesContext, UIComponent uiComponent, Double o) throws ValidatorException {
            if (o == null) {
                FacesMessage msg = new FacesMessage("Введите Y");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
            try {
                if (o >= 5 || o <= -5) {
                    FacesMessage msg = new FacesMessage("Y не входит в ОДЗ: (-5, 5)");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }
            } catch (NumberFormatException exception) {
                FacesMessage msg = new FacesMessage("Y не является числом");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }
}
