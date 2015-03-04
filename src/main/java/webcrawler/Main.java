package webcrawler;

import webcrawler.view.OpenUrlView;

/**
 *
 * Utilisations possibles
 *
 * - chercher plus cours chemin entre une page internet satisfaisant une certaine condition et une page d'origine
 * - collecter de l'information en crawlant le web (emails, images, textes)
 * - visualiser comment les pages sont interconnectées
 * - visualiser comment les domaines sont interconnectés
 *
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 15.03.13
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        new OpenUrlView();
    }
}
