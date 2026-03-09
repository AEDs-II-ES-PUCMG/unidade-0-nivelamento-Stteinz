public class ItemDePedido {

    private Produto produto;
    private int quantidade;
    private double precoVenda;

    
    public ItemDePedido(Produto produto, int quantidade, double precoVenda) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    public double calcularSubtotal() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemDePedido other = (ItemDePedido) obj;
        if (produto == null) {
            if (other.produto != null)
                return false;
        } else if (!produto.equals(other.produto))
            return false;
        if (quantidade != other.quantidade)
            return false;
        if (Double.doubleToLongBits(precoVenda) != Double.doubleToLongBits(other.precoVenda))
            return false;
        return true;
    }

}
