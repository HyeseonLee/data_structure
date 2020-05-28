package cse2010.homework2;

import java.nio.channels.Pipe;
import java.util.Arrays;
import java.util.stream.Collectors;

/*
 * © 2020 CSE2010 HW #2
 *
 * You can freely modify this class except the signatures of the public methods!!
 */
public class DLinkedPolynomial implements Polynomial {

    private DLinkedList<Term> list = null;

    public DLinkedPolynomial() {
        list = new DLinkedList<>();
    }

    public int getDegree() {
        // list 안에 있는 모든 Node<Term>들을 돌면서 expo가 가장 큰 것을 찾는다.

        int degree=0;
        int bef_expo, cur_expo;

        if(termCount()==0) {
            return 0;
        }
        else {
            Node<Term> before = this.list.getFirstNode();
            Node<Term> current = this.list.getNextNode(before);

            while(current != null) {
                bef_expo = before.getInfo().expo;
                cur_expo = current.getInfo().expo;

                if(bef_expo<=cur_expo) {
                    degree = cur_expo;
                }
                else{
                    degree=bef_expo;
                }
                current = this.list.getNextNode(current);
            }
            return degree;
        }

    }

    @Override
    public double getCoefficient(final int expo) {
        Node<Term> term = this.list.find(new Term(0.0, expo), Term::compareExponents);
        if(term!=null){
            return term.getInfo().coeff;
        }
        return 0;
    }

    // 지수(expo)가 같은 Term 2개 더하기
    private Term addTerms(Term x, Term y) {
        return new Term(x.coeff + y.coeff, x.expo);
    }

    @Override
    public Polynomial add(final Polynomial p) {
        double p_coeff, add_coeff;
        int p_degree, my_degree, loop_degree;
        p_degree = p.getDegree();
        my_degree=this.getDegree();

        Polynomial res = new DLinkedPolynomial();

        if(p_degree>=my_degree){
            loop_degree = p_degree;
        }
        else{
            loop_degree=my_degree;
        }

        for(int i=0; i<=loop_degree; i++){
            p_coeff = p.getCoefficient(i);
            if(this.getCoefficient(i) != 0 && p.getCoefficient(i)!=0) {
                //this O p O
                add_coeff = this.getCoefficient(i)+p_coeff;
                res.addTerm(add_coeff, i);
            }
            else if(this.getCoefficient(i)!=0 && p.getCoefficient(i)==0){
                //this O p X
                add_coeff=this.getCoefficient(i);
                res.addTerm(add_coeff, i);
            }
            else if(this.getCoefficient(i)==0 && p.getCoefficient(i)!=0){
                // this X p O
                add_coeff=p.getCoefficient(i);
                res.addTerm(add_coeff, i);
            }
            else{
                // this X p X
                continue;
            }
        }
        return res;
    }

    private Term multTerms(Term x, Term y) {
        // 모든 Term들의 곱셈 방식
        return new Term(x.coeff * y.coeff, x.expo + y.expo);
    }

    @Override
    public Polynomial mult(final Polynomial p) {
        if(this.termCount()==0 && p.termCount()==0) {
            return null;
        }

        Polynomial res = new DLinkedPolynomial();
        int p_degree = p.getDegree();
        int my_degree = this.getDegree();
        Term temp;


        for(int i=0; i<=my_degree; i++){
            if (this.getCoefficient(i) != 0) {
                for(int j=0; j<=p_degree; j++) {
                    if (p.getCoefficient(j) != 0) {
                        temp = multTerms(new Term(this.getCoefficient(i),i), new Term(p.getCoefficient(j),j));
                        res.addTerm(temp.coeff, temp.expo);
                    }
                    else{
                        continue;
                    }
                }
            }
            else{
                continue;
            }
        }

        return res;
    }

    @Override
    public void addTerm(final double coeff, final int expo) {
        // expo 크기 순대로 내림차순으로 add하고 싶은데!!
        Node<Term> input = new Node<>(new Term(coeff, expo)); //새로 추가할 Term
        Node<Term> check = this.list.getFirstNode();

        if(check==null) { //빈 list 였다면 input을 맨 앞에다가 넣어주기.
            this.list.addFirst(input);
        }
        else {
            while (check != null) {
                if (input.getInfo().expo < check.getInfo().expo) {
                    if (this.list.find(new Term(input.getInfo().coeff, input.getInfo().expo), Term::compareExponents) != null) {
                        // 같은 계수 찾음. 2x^5가 있는데, 18x^5가 들어온거야. or 2x^5(본인/모양이같음) 가 들어온거야.
                        // 즉 1.coeff, expo 둘다 같은 경우 2.
                        Node<Term> same_expo = this.list.find(new Term(input.getInfo().coeff, input.getInfo().expo), Term::compareExponents);
                        if(input.equals(same_expo)==true){
                            //same_expo가 input 본인임
                            this.list.remove(input); //node간 연결 끊어야해서 input본인 remove
                            this.list.addAfter(check, input); //그리고 위치 이동해서 다시 연결
                        }
                        else{
                            //same_expo가 input 본인이 아닌, input이랑 똑같이 생긴 항이다
                            same_expo.setInfo(addTerms(same_expo.getInfo(), input.getInfo()));
                            break; //얘는 이미 정렬된 위치가 있으니까 돌면서 정렬할 필요가 없다.
                        }
                    } else {
                        //같은 계수 없습니다.
                        this.list.addAfter(check, input);
                    }

                    } else if (input.getInfo().expo == check.getInfo().expo) {
                        if (check.equals(input) == false) {
                            //본인이 아닐때 check에 합쳐주기
                            check.setInfo(addTerms(check.getInfo(), input.getInfo()));
                            break;
                        }
                        // else일때는 본인이라 아무것도 안함.

                    } else if (input.getInfo().expo > check.getInfo().expo) {

                        if (this.list.find(new Term(input.getInfo().coeff, input.getInfo().expo), Term::compare) != null) {
                            //사실상 이미 있다면 자리 바꿔줄 필요 없어서 아무 행동 안해도 돼요.
                        } else {
                            this.list.addBefore(check, input);
                        }
                    }

                    check = this.list.getNextNode(check); //그 다음 node로 옮겨주기
                }
                // 끝까지 다 돌면서 정렬했다.
            }
        }


    @Override
    public void removeTerm(final int expo) {
        Node<Term> node = list.find(new Term(0, expo), Term::compareExponents);

        if(node==null){
            throw new NoSuchTermExistsException();
        }
        else{
            this.list.remove(node);
        }


    }

    @Override
    public int termCount() {
        return list.size();
    }

    @Override
    public double evaluate(final double val) {
        if(this.termCount()==0){
            return 0;
        }

        int my_degree = this.getDegree();
        double res=0;
        double loop_res = 1.0;

        for(int i=0; i<=my_degree; i++){
            if(this.getCoefficient(i)!=0) {
                loop_res = 1.0;

                //i인 expo가 있다면 expo(i)번 val 곱해주기 -> coeff도 곱하기
                //expo가 1 이상일 때 부터만
                for(int j=1; j<=i; j++){
                    loop_res = loop_res*val;
                }
                loop_res=loop_res*this.getCoefficient(i); //계수까지 곱하기
                res += loop_res;
            }

            else{
                continue;
            }
        }
        return res;
    }

    @Override
    public String toString() {
        if (list.isEmpty())
            return "Empty Polynomial";
        else {
            String[] terms = new String[termCount()];
            int i = 0;
            Node<Term> current = list.getFirstNode();
            do {
                if (current.getInfo().expo == 0) {
                    terms[i++] = String.valueOf(current.getInfo().coeff);
                } else if (current.getInfo().expo == 1) {
                    terms[i++] = current.getInfo().coeff + "x";
                } else {
                    terms[i++] = current.getInfo().coeff + "x^" + current.getInfo().expo;
                }
                current = list.getNextNode(current);
            } while (current != null);
            return String.join("+", terms);
        }
    }

}

