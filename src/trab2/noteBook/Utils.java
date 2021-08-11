package trab2.noteBook;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * M´todos utilitários
 */
public class Utils {
    /**
     * Atualiza um mapa de dados.
     * @param m mapa a atualizar.
     * @param key fornecedor da chave.
     * @param value fornecedor do valor.
     * @param add função que atualiza o mapa.
     * @param <K> tipo de chave.
     * @param <V> tipo de valor.
     * @return retorna true se o mapa foi atualizado.
     */
    public static < K , V > boolean actualize (
            Map< K , V > m , Supplier<K> key , Supplier<V> value , Function< V , Boolean > add
    ) {
        K k = key.get();
        V v = m.get( k );
        if ( v != null )
            return add.apply( v );
        m.put( k , value.get() );
        return true;
    }

    /**
     * Aplicar a ação a todos os valores contidos nas coleções associadas às chaves.
     * @param m contentor que associa a cada chave uma coleção de valores.
     * @param action ação a executar sobre cada valor V
     * @param <K> tipo da chave do contentor associativo.
     * @param <V> tipo do valor de cada elemento da coleção associada.
     */
    public static < K, V > void foreachV ( Map< K , ? extends Collection<V> > m, Consumer<V> action ) {
        m.values().forEach( c -> c.forEach( action ) );
    }

    /**
     * Num contentor associativo obter a coleção de chaves cujos valores associados
     * são os maiores segundo um determinado comparador.
     * @param m contntor associativo
     * @param cmp comparador
     * @return coleção de chaves que têm mais valores
     */
    public static < K , V > Collection< K > greater ( Map< K ,V > m , Comparator<V> cmp ) {
        Collection<K> keys = new ArrayList<>();

        if ( !m.entrySet().iterator().hasNext() )
            return keys;

        Map.Entry<K,V> entry = m.entrySet().iterator().next();
        V great = entry.getValue();

        for ( Map.Entry<K,V> e : m.entrySet() ) {
            int result = cmp.compare( great , e.getValue() );
            if ( result > 0 )
                continue;
            if ( result < 0 ) {
                keys.clear();
                great = e.getValue();
            }
            keys.add( e.getKey() );
        }

        return keys;
    }
}
