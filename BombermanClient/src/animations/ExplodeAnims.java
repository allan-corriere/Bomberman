package animations;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import gameobject.attribute.GameObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ExplodeAnims  {
	
	//Images bomb center 
	private Image explosionCenter1 = new Image(new File("ressources/Bombes/radius1/center1_3.png").toURI().toString());
	private Image explosionCenter2 = new Image(new File("ressources/Bombes/radius1/center2.png").toURI().toString());
	private Image explosionCenter3 = new Image(new File("ressources/Bombes/radius1/center3.png").toURI().toString());
	private Image explosionCenter4 = new Image(new File("ressources/Bombes/radius1/center4.png").toURI().toString());
	private Image explosionCenter5 = new Image(new File("ressources/Bombes/radius1/center5.png").toURI().toString());

	private ImageView bombAnimCenter1 = new ImageView(explosionCenter1);
	private ImageView bombAnimCenter2 = new ImageView(explosionCenter2);
	private ImageView bombAnimCenter3 = new ImageView(explosionCenter3);
	private ImageView bombAnimCenter4 = new ImageView(explosionCenter4);
	private ImageView bombAnimCenter5 = new ImageView(explosionCenter5);
	
	
	//Images bomb down 
	
	private Image explosionDown1 = new Image(new File("ressources/Bombes/radius1/down1.png").toURI().toString());
	private Image explosionDown2 = new Image(new File("ressources/Bombes/radius1/down1.png").toURI().toString());
	private Image explosionDown3 = new Image(new File("ressources/Bombes/radius1/down1.png").toURI().toString());
	private Image explosionDown4 = new Image(new File("ressources/Bombes/radius1/down1.png").toURI().toString());
	private Image explosionDown5 = new Image(new File("ressources/Bombes/radius1/down1.png").toURI().toString());

	private ImageView bombAnimDown1 = new ImageView(explosionDown1);
	private ImageView bombAnimDown2 = new ImageView(explosionDown2);
	private ImageView bombAnimDown3 = new ImageView(explosionDown3);
	private ImageView bombAnimDown4 = new ImageView(explosionDown4);
	private ImageView bombAnimDown5 = new ImageView(explosionDown5);

	
	//Images up bomb 
	private Image explosionUp1 = new Image(new File("ressources/Bombes/radius1/up1.png").toURI().toString());
	private Image explosionUp2 = new Image(new File("ressources/Bombes/radius1/up2.png").toURI().toString());
	private Image explosionUp3 = new Image(new File("ressources/Bombes/radius1/up3.png").toURI().toString());
	private Image explosionUp4 = new Image(new File("ressources/Bombes/radius1/up4.png").toURI().toString());
	private Image explosionUp5 = new Image(new File("ressources/Bombes/radius1/up5.png").toURI().toString());
	
	private ImageView bombAnimUp1 = new ImageView(explosionUp1);
	private ImageView bombAnimUp2 = new ImageView(explosionUp2);
	private ImageView bombAnimUp3 = new ImageView(explosionUp3);
	private ImageView bombAnimUp4 = new ImageView(explosionUp4);
	private ImageView bombAnimUp5 = new ImageView(explosionUp5);
	
	//Images bomb left 
	private Image explosionLeft1 = new Image(new File("ressources/Bombes/radius1/left1.png").toURI().toString());
	private Image explosionLeft2 = new Image(new File("ressources/Bombes/radius1/left2.png").toURI().toString());
	private Image explosionLeft3 = new Image(new File("ressources/Bombes/radius1/left3.png").toURI().toString());
	private Image explosionLeft4 = new Image(new File("ressources/Bombes/radius1/left4.png").toURI().toString());
	private Image explosionLeft5 = new Image(new File("ressources/Bombes/radius1/left5.png").toURI().toString());

	private ImageView bombAnimLeft1 = new ImageView(explosionLeft1);
	private ImageView bombAnimLeft2 = new ImageView(explosionLeft2);
	private ImageView bombAnimLeft3 = new ImageView(explosionLeft3);
	private ImageView bombAnimLeft4 = new ImageView(explosionLeft4);
	private ImageView bombAnimLeft5 = new ImageView(explosionLeft5);
	
	//Images bomb right 
	
	private Image explosionRight1 = new Image(new File("ressources/Bombes/radius1/right1.png").toURI().toString());
	private Image explosionRight2 = new Image(new File("ressources/Bombes/radius1/right2.png").toURI().toString());
	private Image explosionRight3 = new Image(new File("ressources/Bombes/radius1/right3.png").toURI().toString());
	private Image explosionRight4 = new Image(new File("ressources/Bombes/radius1/right4.png").toURI().toString());
	private Image explosionRight5 = new Image(new File("ressources/Bombes/radius1/right5.png").toURI().toString());

	private ImageView bombAnimRight1 = new ImageView(explosionRight1);
	private ImageView bombAnimRight2 = new ImageView(explosionRight2);
	private ImageView bombAnimRight3 = new ImageView(explosionRight3);
	private ImageView bombAnimRight4 = new ImageView(explosionRight4);
	private ImageView bombAnimRight5 = new ImageView(explosionRight5);

	private int radius ; 
	
	private Pane RBox;
	
	private boolean blockedXPlus;
	private boolean blockedYPlus;
	private boolean blockedXMinus;
	private boolean blockedYMinus;
	private boolean destructXplus; 
	private boolean destructXMinus; 
	private boolean destructYPlus; 
	private boolean destructYMinus; 




	public void configAnims(ImageView bombAnim,double bombX, double bombY) {
		bombAnim.setX(bombX);
		bombAnim.setY(bombY);
		bombAnim.setFitHeight(50);
		bombAnim.setVisible(false);
		bombAnim.setFitWidth(50);
	}

	
	public ExplodeAnims(List<GameObject> gameObjectList,Pane RBox, double bombX, double bombY, int radius, boolean blockedXPlus, boolean blockedYPlus, boolean blockedXMinus, boolean blockedYMinus, boolean destructXplus, boolean destructXminus, boolean destructYPlus, boolean destructYMinus) {
		
		this.blockedXMinus=blockedXMinus;
		this.blockedXPlus=blockedXPlus;
		this.blockedYMinus = blockedYMinus;
		this.blockedYPlus = blockedYPlus;
		this.destructXplus = destructXplus;
		this.destructXMinus = destructXminus;
		this.destructYPlus = destructYPlus;
		this.destructYMinus = destructYMinus;
		
		this.RBox = RBox;
		
		configAnims(bombAnimCenter1, bombX, bombY);
		configAnims(bombAnimCenter2, bombX, bombY);
		configAnims(bombAnimCenter3, bombX, bombY);
		configAnims(bombAnimCenter4, bombX, bombY);
		configAnims(bombAnimCenter5, bombX, bombY);

		configAnims(bombAnimLeft1, bombX-50, bombY);
		configAnims(bombAnimLeft2, bombX-50, bombY);
		configAnims(bombAnimLeft3, bombX-50, bombY);
		configAnims(bombAnimLeft4, bombX-50, bombY);
		configAnims(bombAnimLeft5, bombX-50, bombY);

		configAnims(bombAnimRight1, bombX+50, bombY);
		configAnims(bombAnimRight2, bombX+50, bombY);
		configAnims(bombAnimRight3, bombX+50, bombY);
		configAnims(bombAnimRight4, bombX+50, bombY);
		configAnims(bombAnimRight5, bombX+50, bombY);
		
		configAnims(bombAnimUp1, bombX, bombY-50);
		configAnims(bombAnimUp2, bombX, bombY-50);
		configAnims(bombAnimUp3, bombX, bombY-50);
		configAnims(bombAnimUp4, bombX, bombY-50);
		configAnims(bombAnimUp5, bombX, bombY-50);

		configAnims(bombAnimDown1, bombX, bombY+50);
		configAnims(bombAnimDown2, bombX, bombY+50);
		configAnims(bombAnimDown3, bombX, bombY+50);
		configAnims(bombAnimDown4, bombX, bombY+50);
		configAnims(bombAnimDown5, bombX, bombY+50);		

		//Ajout des animations 
		
		RBox.getChildren().add(bombAnimCenter1);
		RBox.getChildren().add(bombAnimLeft1);
		RBox.getChildren().add(bombAnimDown1);
		RBox.getChildren().add(bombAnimUp1);
		RBox.getChildren().add(bombAnimRight1);		
		
		//Ajout des secondes animations 
		
		RBox.getChildren().add(bombAnimCenter2);
		RBox.getChildren().add(bombAnimLeft2);
		RBox.getChildren().add(bombAnimDown2);
		RBox.getChildren().add(bombAnimUp2);
		RBox.getChildren().add(bombAnimRight2);		
		
	//Ajout des troisièmes animations 
		
		RBox.getChildren().add(bombAnimCenter3);
		RBox.getChildren().add(bombAnimLeft3);
		RBox.getChildren().add(bombAnimDown3);
		RBox.getChildren().add(bombAnimUp3);
		RBox.getChildren().add(bombAnimRight3);		
		
	//Ajout des quatrièmes animations 
		
		RBox.getChildren().add(bombAnimCenter4);
		RBox.getChildren().add(bombAnimLeft4);
		RBox.getChildren().add(bombAnimDown4);
		RBox.getChildren().add(bombAnimUp4);
		RBox.getChildren().add(bombAnimRight4);		
		
	//Ajout des cinquiemes  animations 
		
		RBox.getChildren().add(bombAnimCenter5);
		RBox.getChildren().add(bombAnimLeft5);
		RBox.getChildren().add(bombAnimDown5);
		RBox.getChildren().add(bombAnimUp5);
		RBox.getChildren().add(bombAnimRight5);		
		
		
		
		
	/*	
			//Retrait des premières animations 
				
				RBox.getChildren().remove(bombAnimCenter1);
				RBox.getChildren().remove(bombAnimLeft1);
				RBox.getChildren().remove(bombAnimDown1);
				RBox.getChildren().remove(bombAnimUp1);
				RBox.getChildren().remove(bombAnimRight1);
				
		*/

	}
	
	//Enlever le lien entre le pane et les anims 
	
	public void removeAll() {
		
		RBox.getChildren().remove(bombAnimCenter1);
		RBox.getChildren().remove(bombAnimLeft1);
		RBox.getChildren().remove(bombAnimDown1);
		RBox.getChildren().remove(bombAnimUp1);
		RBox.getChildren().remove(bombAnimRight1);
		
		RBox.getChildren().remove(bombAnimCenter2);
		RBox.getChildren().remove(bombAnimLeft2);
		RBox.getChildren().remove(bombAnimDown2);
		RBox.getChildren().remove(bombAnimUp2);
		RBox.getChildren().remove(bombAnimRight2);
		
		RBox.getChildren().remove(bombAnimCenter3);
		RBox.getChildren().remove(bombAnimLeft3);
		RBox.getChildren().remove(bombAnimDown3);
		RBox.getChildren().remove(bombAnimUp3);
		RBox.getChildren().remove(bombAnimRight3);
		
		RBox.getChildren().remove(bombAnimCenter4);
		RBox.getChildren().remove(bombAnimLeft4);
		RBox.getChildren().remove(bombAnimDown4);
		RBox.getChildren().remove(bombAnimUp4);
		RBox.getChildren().remove(bombAnimRight4);
		
		RBox.getChildren().remove(bombAnimCenter5);
		RBox.getChildren().remove(bombAnimLeft5);
		RBox.getChildren().remove(bombAnimDown5);
		RBox.getChildren().remove(bombAnimUp5);
		RBox.getChildren().remove(bombAnimRight5);
		
	}
	// Fonction qui check ou afficher l'animation 
	
	public void setVisible() {
		
		Timer t = new Timer();	
		
		//Animation 2 
		TimerTask t2 = new TimerTask() {
			@Override
			public void run() {
				
				System.out.println("1ère anim");
				//Gestion de la visibilité 

					bombAnimCenter2.setVisible(true);

					if (blockedXMinus == false || destructXMinus == true) {
						bombAnimLeft2.setVisible(true);

					}
					if (blockedXPlus == false || destructXplus == true) {
						bombAnimRight2.setVisible(true);
					}
					
					if (blockedYPlus==false || destructYPlus == true ) {
						bombAnimDown2.setVisible(true);

					}
					
					if (blockedYMinus == false || destructYMinus == true) {
						bombAnimUp2.setVisible(true);
					}
				
				}
			
		};
		
		//Animation 3 
		TimerTask t3 = new TimerTask() {

			@Override
			public void run() {
				
				System.out.println("2eme anim");

				
					bombAnimCenter3.setVisible(true);

					if (blockedXMinus == false || destructXMinus == true) {
						bombAnimLeft3.setVisible(true);

					}
					if (blockedXPlus == false || destructXplus == true) {
						bombAnimRight3.setVisible(true);
					}
					
					if (blockedYPlus==false || destructYPlus == true ) {
						bombAnimDown3.setVisible(true);

					}
					
					if (blockedYMinus == false || destructYMinus == true) {
						bombAnimUp3.setVisible(true);
					}
				
				}
			
		};
		
		
		//Animation 4
		TimerTask t4 = new TimerTask() {

			@Override
			public void run() {
				System.out.println("3eme anim");

				
					bombAnimCenter4.setVisible(true);

					if (blockedXMinus == false || destructXMinus == true) {
						bombAnimLeft4.setVisible(true);

					}
					if (blockedXPlus == false || destructXplus == true) {
						bombAnimRight4.setVisible(true);
					}
					
					if (blockedYPlus==false || destructYPlus == true) {
						bombAnimDown4.setVisible(true);

					}
					
					if (blockedYMinus == false || destructYMinus == true) {
					bombAnimUp4.setVisible(true);
					}
				
				
				}
			
		};
		
		
		//Animation 5 
		TimerTask t5 = new TimerTask() {

			@Override
			public void run() {
				System.out.println("4eme anim");

				
					bombAnimCenter5.setVisible(true);

					if (blockedXMinus == false || destructXMinus == true) {
						bombAnimLeft5.setVisible(true);

					}
					if (blockedXPlus == false || destructXplus == true) {
						bombAnimRight5.setVisible(true);
					}
					
					if (blockedYPlus==false || destructYPlus == true ) {
						bombAnimDown5.setVisible(true);

					}
					
					if (blockedYMinus == false || destructYMinus == true) {
						bombAnimUp5.setVisible(true);
					}
				
				}
			
		};
		
		
		
		//Effacement des animations 
				TimerTask t0 = new TimerTask() {

					@Override
					public void run() {
						System.out.println("efface anim");

							bombAnimCenter1.setVisible(false);
							bombAnimCenter2.setVisible(false);
							bombAnimCenter3.setVisible(false);
							bombAnimCenter4.setVisible(false);
							bombAnimCenter5.setVisible(false);


								
								bombAnimLeft1.setVisible(false);
								bombAnimLeft2.setVisible(false);
								bombAnimLeft3.setVisible(false);
								bombAnimLeft4.setVisible(false);
								bombAnimLeft5.setVisible(false);

							

								bombAnimRight1.setVisible(false);
								bombAnimRight2.setVisible(false);
								bombAnimRight3.setVisible(false);
								bombAnimRight4.setVisible(false);
								bombAnimRight5.setVisible(false);
							
							

								bombAnimDown1.setVisible(false);
								bombAnimDown2.setVisible(false);
								bombAnimDown3.setVisible(false);
								bombAnimDown4.setVisible(false);
								bombAnimDown5.setVisible(false);

							
							
				
								bombAnimUp1.setVisible(false);
								bombAnimUp2.setVisible(false);
								bombAnimUp3.setVisible(false);
								bombAnimUp4.setVisible(false);
								bombAnimUp5.setVisible(false);
							
					
					}
				};		
				
			//Animation 1 
			TimerTask t1 = new TimerTask() {

				@Override
				public void run() {
					System.out.println("0 anim");

					bombAnimCenter1.setVisible(true);
					
					if (blockedXMinus == false || destructXMinus == true) {
						bombAnimLeft1.setVisible(true);
				
					}
					if (blockedXPlus == false || destructXplus == true) {
						bombAnimRight1.setVisible(true);
			
			
					}
					
					if (blockedYPlus==false ) {
						bombAnimDown1.setVisible(true);
			
					}
					
					if (blockedYMinus == false) {
						bombAnimUp1.setVisible(true);
					}
			
					}
				
			};
		
	
		t.schedule(t1, 0);
		t.schedule(t2, 100);
		t.schedule(t3, 200);
		t.schedule(t4, 300);
		t.schedule(t5, 400);
		t.schedule(t0, 500);
		
				
		
	}



}
