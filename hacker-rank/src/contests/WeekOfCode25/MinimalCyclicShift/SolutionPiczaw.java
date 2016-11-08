package contests.WeekOfCode25.MinimalCyclicShift;

import java.io.*;
import java.util.*;

public class SolutionPiczaw {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        int n=Fs.nextInt();
        int k=Fs.nextInt();
        if(k>n-2){
            Fs.pl(0);
            Fs.flush();
            return;
        }
        long[] a=Fs.nextLongArray(n);
        long[] b=Fs.nextLongArray(n);
        long mod=998244353;
        NTT ntt=new NTT(mod,3);
        long[] niko=new long[k+2];
        long prev=1;
        niko[0]=1;
        for(int i=1;i<k+2;i++){
            prev=-(prev*(k-i+2)%mod*Fs.inv(i,mod))%mod;
            prev=(prev+mod)%mod;
            niko[i]=prev;
        }
        
            
            a=kaisa(a,niko,ntt,mod);
            b=kaisa(b,niko,ntt,mod);//Fs.pl(Arrays.toString(niko));
            for(int i=0;i<n;i++){
                boolean bo=true;
                for(int j=0;j<n-k-1;j++){
                    if(a[j]!=b[(i+j)%n]){
                        bo=false;
                        break;
                    }
                }
                if(bo){
                    Fs.pl(i);
                    Fs.flush();
                    return;
                }
            }
            Fs.pl(-1);
        
        Fs.flush();
    }
    static long[] kaisa(long[] a,long[] niko,NTT ntt,long mod){
        long[] c=new long[a.length*2];
        for(int i=0;i<2;i++){
            for(int j=0;j<a.length;j++){
                c[i*a.length+j]=a[j];
            }
        }
        c= ntt.convolution(c,niko);
        long[] d=new long[a.length];
        for(int i=0;i<a.length;i++){
            d[i]=c[niko.length-1+i];
        }
        return d;
    }
}
class Fs {
	//static{Runtime.getRuntime().addShutdownHook(new Thread(()->flush()));}
	private static final byte[] buffer = new byte[1024];
	private static int ptr = 0;
	private static int buflen = 0;
	private static boolean hasNextByte() {
		if (ptr < buflen)  return true;
		else{
			ptr = 0;
			try {
				buflen = System.in.read(buffer);
			} catch (IOException e) {e.printStackTrace();}
			if (buflen <= 0)  return false;
		}
		return true;
	}
	private static int readByte() { if (hasNextByte()) return buffer[ptr++]; else return -1;}
	private static boolean isPrintableChar(int c) { return 33 <= c && c <= 126;}
	private static void skipUnprintable() { while(hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++;}
	public static boolean hasNext() { skipUnprintable(); return hasNextByte();}
	public static String next() {
		if (!hasNext()) throw new NoSuchElementException();
		StringBuilder sb = new StringBuilder();
		int b = readByte();
		while(isPrintableChar(b)) {
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}
	public static int nextInt() {return (int)nextLong();}
	public static long nextLong() {
		if (!hasNext()) throw new NoSuchElementException();
		long n = 0;
		boolean minus = false;
		int b = readByte();
		if (b == '-') {
			minus = true;
			b = readByte();
		}
		if (b < '0' || '9' < b)  throw new NumberFormatException();
		while(true){
			if ('0' <= b && b <= '9') n = n * 10 + b-'0';
			else if(b == -1 || !isPrintableChar(b)) return minus ? -n : n;
			else throw new NumberFormatException();
			b = readByte();
		}
	}
	public static long[] nextLongArray(int i){
		long[] result=new long[i];
		for(int j=0;j<i;j++)  result[j]=nextLong();
		return result;
	}
	public static void nextLongArray(long[]... arrays){
		for(int j=0;j<arrays[0].length;j++)
			for(long[] array:arrays) array[j]=nextLong();
	}
	public static int[] nextIntArray(int i){
		int[] result=new int[i];
		for(int j=0;j<i;j++)  result[j]=nextInt();
		return result;
	}
	public static void nextIntArray(int[]... arrays){
		for(int j=0;j<arrays[0].length;j++)
			for(int[] array:arrays)  array[j]=nextInt();
	}
	static StringBuilder sb=new StringBuilder();
	public static void flush(){
		System.out.print(sb);
		sb=new StringBuilder();
	}
	public static void pr(double[] o,String str){for(Object ob:o) sb.append(ob).append(str);}
	public static void pl(double[] o,String str){
		pr(o,str);
		pl();
	}
	public static void pr(Object o){sb.append(o);}
	public static void pl(Object o){sb.append(o).append("\n");}
	public static void pl(){sb.append("\n");}
	public static int[] prefSum(int[] la){
		int[] result=new int[la.length];
		for(int i=0;i<la.length;i++) result[i]=(i==0)?la[i]:result[i-1]+la[i];
		return result;
	}
	public static long[] prefSum(long[] la,long mod){
		long[] result=new long[la.length];
		for(int i=0;i<la.length;i++) result[i]=(i==0)?(la[i]+mod)%mod:(result[i-1]+la[i]+mod)%mod;
		return result;
	}
	public static int lastLowerIndex(long[] array,long lo){return lastLowerOrEqualIndex(array,lo-1);}
	public static int lastLowerOrEqualIndex(long[] array,long lo){
		if(array[array.length-1]<=lo){return array.length-1;}
		if(array[0]>lo){return -1;}
		int tmax=array.length-1, tmin=0;
		int haba=tmax-tmin;
		int tindex=tmin+haba/2;
		while(haba>0){
			if(array[tindex]<=lo){tmin=tindex;}
			else{tmax=tindex-1;}
			haba=tmax-tmin;
			tindex=tmin+haba/2+(haba==1?1:0);
		}
		return tindex;
	}
	public static long gcd(long a,long b){
		if(a>b)a%=b;
		while(a>0){
			b%=a;
			if(b==0)return a;
			a%=b;
		}
		return b;
	}
	public static long modPow(long a,long b,long mod){
		long c=1;
		while(b>0){
			if(b%2==1)  c=(c*a)%mod;
			a=(a*a)%mod;
			b/=2;
		}
		return c;
	}
	public static long inv(long a,long mod){
		long b=mod;
		long p = 1, q = 0;
		while (b > 0) {
			long c = a / b;
			long d=a;
			a = b;
			b = d % b;
			d = p;
			p = q;
			q = d - c * q;
		}
		return p < 0 ? p + mod : p;
	}
	static long time=0;
	public static void keisoku(){
		if(time==0) time=System.nanoTime();
		else{
			long t=System.nanoTime();
			pl((t-time)/1000000000.0+"sec");
			time=t;
		}
	}
}
class FFT {
	public static double[] fft(final double[] inputReal, double[] inputImag,
	boolean DIRECT) {
		int n = inputReal.length;
		
		// If n is a power of 2, then ld is an integer (_without_ decimals)
		double ld = Math.log(n) / Math.log(2.0);
		
		if (((int) ld) - ld != 0) {
			throw new RuntimeException("The number of elements is not a power of 2.");
		}
		
		int nu = (int) ld;
		int n2 = n / 2;
		int nu1 = nu - 1;
		double[] xReal = new double[n];
		double[] xImag = new double[n];
		double tReal, tImag, p, arg, c, s;
		
		// Here I check if I'm going to do the direct transform or the inverse
		// transform.
		double constant;
		if (DIRECT)
			constant = -2 * Math.PI;
		else
			constant = 2 * Math.PI;
		
		for (int i = 0; i < n; i++) {
			xReal[i] = inputReal[i];
			xImag[i] = inputImag[i];
		}
		
		// First phase - calculation
		int k = 0;
		for (int l = 1; l <= nu; l++) {
			while (k < n) {
				for (int i = 1; i <= n2; i++) {
					p = bitreverseReference(k >> nu1, nu);
					// direct FFT or inverse FFT
					arg = constant * p / n;
					c = Math.cos(arg);
					s = Math.sin(arg);
					tReal = xReal[k + n2] * c + xImag[k + n2] * s;
					tImag = xImag[k + n2] * c - xReal[k + n2] * s;
					xReal[k + n2] = xReal[k] - tReal;
					xImag[k + n2] = xImag[k] - tImag;
					xReal[k] += tReal;
					xImag[k] += tImag;
					k++;
				}
				k += n2;
			}
			k = 0;
			nu1--;
			n2 /= 2;
		}
		
		// Second phase - recombination
		k = 0;
		int r;
		while (k < n) {
			r = bitreverseReference(k, nu);
			if (r > k) {
				tReal = xReal[k];
				tImag = xImag[k];
				xReal[k] = xReal[r];
				xImag[k] = xImag[r];
				xReal[r] = tReal;
				xImag[r] = tImag;
			}
			k++;
		}
		
		double[] newArray = new double[xReal.length * 2];
		double radice = DIRECT ? 1 : 1.0 / n;
		for (int i = 0; i < newArray.length; i += 2) {
			int i2 = i / 2;
			newArray[i] = xReal[i2] * radice;
			newArray[i + 1] = xImag[i2] * radice;
		}
		return newArray;
	}
	private static int bitreverseReference(int j, int nu) {
		int j2;
		int j1 = j;
		int k = 0;
		for (int i = 1; i <= nu; i++) {
			j2 = j1 / 2;
			k = 2 * k + j1 - 2 * j2;
			j1 = j2;
		}
		return k;
	}
	public static double[] convolution(double[] a,double[] b){
		int n = a.length + b.length - 1;
		int m = Integer.highestOneBit(n)*2;
		double[] c= Arrays.copyOf(a,m);
		double[] d= Arrays.copyOf(b,m);
		double[] imag=new double[m];
		a=fft(c,imag,true);
		b=fft(d,imag,true);
		double[] re=new double[m];
		double[] im=new double[m];
		for(int i=0;i<m;i++){
			re[i]=a[2*i]*b[2*i]-a[2*i+1]*b[2*i+1];
			im[i]=a[2*i]*b[2*i+1]+a[2*i+1]*b[2*i];
		}
		double[] ifft=fft(re,im,false);
		double[] result=new double[n];
		for(int i=0;i<n;i++)result[i]=ifft[2*i];
		return result;
	}
	public static long[] convolution(long[] a,long[] b){
		double[] c=new double[a.length];
		for(int i=0;i<a.length;i++)c[i]=a[i];
		double[] d=new double[b.length];
		for(int i=0;i<b.length;i++)d[i]=b[i];
		double[] res=convolution(c,d);
		long[] result=new long[res.length];
		for(int i=0;i<res.length;i++)result[i]=Math.round(res[i]);
		return result;
	}
}
class NTT {
	long mod, root;
	NTT(long mod, long root) {
		this.mod = mod;
		this.root = root;
	}
	long[] ntt(long[] a, int sign) {
		int n = a.length;
		long base = modpow(root, (mod - 1) / n, mod);
		if (sign == -1) base = modinv(base, mod);
		for (int m = n; m >= 2; m >>>= 1) {
			int mh = m >>> 1;
			long w = 1;
			for (int i = 0; i < mh; i++) {
				for (int j = i; j < n; j += m) {
					int k = j + mh;
					long x = (a[j] - a[k] + mod) % mod;
					a[j] = (a[j] + a[k]) % mod;
					a[k] = w * x % mod;
				}
				w = w * base % mod;
			}
			base = base * base % mod;
		}
		int i = 0;
		for (int j = 1; j < n - 1; j++) {
			for (int k = n >> 1; k > (i ^= k); k >>= 1) ;
			if (j > i) {
				long ai = a[i];
				a[i] = a[j];
				a[j] = ai;
			}
		}
		if (sign == -1) {
			long inv = modinv(n, mod);
			for (int j = 0; j < n; j++) a[j] = a[j] * inv % mod;
			}
		return a;
	}
	
	long modpow(long a, long b, long mod) {
		long res = 1;
		while (b > 0) {
			if ((b & 1) != 0) res = res * a % mod;
			a = a * a % mod;
			b /= 2;
		}
		return res;
	}
	long modinv(long a, long mod) {
		if (a == 0) return -1;
		if (gcd(a, mod) != 1) return -1;
		return modulo(extgcd(a, mod)[0], mod);
	}
	long modulo(long a, long mod) {
		a %= mod;
		a += mod;
		a %= mod;
		return a;
	}
	long gcd(long x, long y) {
		return y > 0 ? gcd(y, x % y) : x;
	}
	long[] extgcd(long a, long b) {
		if (b == 0) return new long[]{1, 0};
		long[] p = extgcd(b, a % b);
		return new long[]{p[1], p[0] - a / b * p[1]};
	}
	
	long[] convolution(long[] a, long[] b) {
		int n=a.length+b.length-1;
		int m=Integer.highestOneBit(n-1)*2;
		a=Arrays.copyOf(a,m);
		b=Arrays.copyOf(b,m);
		a = ntt(a, 1);
		b = ntt(b, 1);
		for (int i = 0; i < a.length; i++) a[i] = a[i] * b[i] % mod;
			a = ntt(a, -1);
		return Arrays.copyOf(a,n);
	}
}
