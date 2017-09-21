package sw.hornRule.models;

/**
 * @author
 *
 */
public class Variable extends Formalism {

    protected String variableName;

    // Deux variables specifiques, Vrai et False
    public final static Variable False = new Variable("False");
    public final static Variable True = new Variable("True");

    public Variable() {
        this.variableName = "UnknownVariable";
    }

    public Variable(String variableName) {
        super();
        this.variableName = variableName;
    }

    public String getNomVariable() {
        return variableName;
    }

    public void setNomVariable(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return variableName;
    }

    @Override
    public int hashCode() {
        return variableName != null ? variableName.hashCode() : 0;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Variable
                && (this.variableName == ((Variable) obj).variableName
                || (this.variableName != null && ((Variable) obj).variableName != null && this.variableName.equals(((Variable) obj).variableName)));
    }

}
