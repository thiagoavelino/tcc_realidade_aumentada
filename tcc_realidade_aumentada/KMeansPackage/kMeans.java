package KMeansPackage;

import java.util.ArrayList;
import java.util.Random;

public class kMeans{
	private ArrayList<Ponto> pontos;
	private ArrayList<Ponto> centroides;
	public kMeans(ArrayList<Ponto> p){
		pontos=p;
		centroides = new ArrayList<Ponto>();
	}
	private void setRandomCentroids(int n){
		Random rnd = new Random(System.nanoTime());
		ArrayList<Integer> listaItens = new ArrayList<Integer>();
		int i=0, k;
		centroides.clear();
		while(i<n){
			k=rnd.nextInt(pontos.size());
			if(!listaItens.contains(k)){
				listaItens.add(k);
				centroides.add(new Ponto(pontos.get(k)));
				//System.out.println(centroides.get(i));
				i++;
			}
		}
	}
	private boolean VerificaParada(ArrayList<Ponto> A, ArrayList<Ponto> B){
		for(int i=0; i<A.size(); i++){
			if(!Ponto.equalCoordinates(A.get(i),B.get(i)))
				return false;
		}
		return true;
	}
	public void ExecutaKMeans(int n){
		ArrayList<Ponto> V = new ArrayList<Ponto>(n);
		ArrayList<Integer> nC = new ArrayList<Integer>(n);
		boolean stop=false;
		this.setRandomCentroids(n);
		//System.out.println("\ninicio kmeans");
		while(!stop){
			//O proximo for alcula a que centroide o ponto pertence
			for(int j=0; j<pontos.size(); j++){
				float d=Integer.MAX_VALUE;
				for(int i=0; i<n; i++){
					float dist = Ponto.getDistance(pontos.get(j), centroides.get(i));
					if(dist<d){
						d=dist;
						pontos.get(j).setCentroid(i);
						//System.out.println(pontos.get(j)+"->"+i);
					}
				}
				//System.out.println(pontos.get(j));
			}
			//System.out.println("--\\--");
			//Os tres proximos for acham o novo centroide
			V.clear();
			nC.clear();
			for(int j=0; j<n; j++){
				V.add(new Ponto(0,0));
				nC.add(0);
			}
			Ponto pontoTmp;
			for(int i=0; i<pontos.size(); i++){
				int index = pontos.get(i).getCentroid();
				Ponto p = pontos.get(i);
				Ponto valueV = V.get(index);
				pontoTmp = Ponto.add(p,valueV);
				V.set(index,pontoTmp);
				nC.set(index, nC.get(index)+1);
			}
			System.out.println("Soma pontos V");
			for(int i=0; i<V.size(); i++){
				//System.out.println(V.get(i));
				float vx,vy;
				if(nC.get(i)!=0){
					vx = V.get(i).getX()/nC.get(i);
					vy = V.get(i).getY()/nC.get(i);
				}else{
					vx = V.get(i).getX();
					vy = V.get(i).getY();
				}
				Ponto pMedia = new Ponto(vx,vy,i);
				//System.out.println("Media:"+pMedia);
				V.set(i,pMedia);
			}
			//Verifica criério de parada
			stop=this.VerificaParada(centroides, V);
			if(!stop){
				for(int i=0; i<centroides.size(); i++)
					centroides.set(i,V.get(i));
			}
		}
	}
	
	public ArrayList<Ponto> getProcessedData(){
		return pontos;
	}
}
