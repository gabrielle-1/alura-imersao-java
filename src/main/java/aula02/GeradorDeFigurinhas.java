package aula02;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GeradorDeFigurinhas {
    public void cria(InputStream inputStream, String nomeArquivo) throws IOException {

        //leitura da imagem
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        //cria uma nova imagem na memória com o fundo transparente e novo tamanho
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        var novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);//fundo transparente

        //copiar a imagem original para a nova imagem(em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        //configurar a fonte
        var fonte = new Font(Font.SERIF, Font.BOLD, 60);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);

        //escrever a frase na nova imagem
        graphics.drawString("TOP", 100, novaAltura - 100);

        //escrever a nova imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File("saida/" + nomeArquivo));

    }
}
