package br.com.willbigas.controller;


import br.com.willbigas.model.Frota;

public class FrotaController {

    private CarroController carroController;

    public FrotaController() {
        carroController = new CarroController();

    }

    public Frota calcular(Frota frota) {
        frota.setConsumoMedioDaFrota(carroController.calcularMedia(frota.getCarros()));
        return frota;
    }

}
