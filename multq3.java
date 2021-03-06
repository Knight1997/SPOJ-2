import java.io.*;
import java.util.*;

public class multq3
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
    }
    
    static class IntervalTree {
        public IntervalTree right = null, left = null;
        public int start, end, zero, one, two, lazy;
        public IntervalTree (int _start, int _end) {
            lazy = 0; zero = (end - start + 1); one = 0; two = 0;
            start = _start;
            end = _end;
            int mid = (start + end) >> 1;
            if (start != end) {
                left = new IntervalTree (start, mid);
                right = new IntervalTree (mid + 1, end);
            }
        }
        
        private void cycle () {
            int temp = two;
            two = one;
            one = zero;
            zero = temp;
        }
        
        public void update (int b, int e) {
            if (b > end || start > e) return;
            if (b == start && end == e) {
                left.cycle ();
                right.cycle ();
            }
        }
        
        public int count (int b, int e) {
            if (b > end || start > e) return 0;
            if (b == start && end == e) return zero;
            int mid = (start + end) >> 1, ans = 0;
            if (b > mid) ans = right.count (b, e);
            else if (e <= mid) ans = left.count (b, e);
            else ans = left.count (b, mid) + right.count (mid + 1, e);
            return ans;            
        }
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name)throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine()throws IOException{byte[] buf=new byte[1024];int cnt=0,c;
        while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
    public int nextInt()throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
        if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
    public long nextLong()throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
        if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
    public double nextDouble()throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
    private void fillBuffer()throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;}
    private byte read()throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
    public void close()throws IOException{if(din==null) return;din.close();}
}