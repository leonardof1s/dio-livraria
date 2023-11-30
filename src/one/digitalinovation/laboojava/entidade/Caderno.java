package one.digitalinovation.laboojava.entidade;

import one.digitalinovation.laboojava.entidade.constantes.Materia;

/*
 * @author leonardo ferreira
 */
public class Caderno extends Produto {

    /**
     * Materias do caderno.
     */
    private Materia materias;

    /**
     * @return String return the materias
     */
    public Materia getMateria() {
        return materias;
    }

    /**
     * @param materias the materias to set
     */

    public void setMateria(Materia materia) {
        this.materias = materia;
    }

    @Override
    public double calcularFrete() {
        return (getPreco() * getQuantidade()) + materias.getFator();

    }

    @Override
    public String toString() {
        return "Livro{" +
                "materia='" + materias + '\'' +
                ", codigo='" + getCodigo() + '\'' +
                ", preço='" + getPreco() + '\'' +
                '}';
    }

}