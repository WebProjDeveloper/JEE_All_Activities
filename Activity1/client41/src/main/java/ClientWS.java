import proxy.BanqueService;
import proxy.BanqueWs;
import proxy.Compte;

import java.util.List;

public class ClientWS {
    public static void main(String[] args) {
        BanqueService stub = new BanqueWs().getBanqueServicePort();
        System.out.println(stub.convert(7600));
        Compte cp = stub.getCompte(5);
        System.out.println(cp.getCode());
        System.out.println(cp.getSolde());
    }
}
