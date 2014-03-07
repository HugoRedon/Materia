
package termo.equilibrium;

/**
 *
 * @author
 * Hugo
 */
public interface EquilibriaFunction {
    public double errorFunction(double equilibriaRelation );
    public double newVariableFunction(double variable, double variable_, double e, double e_);
}
