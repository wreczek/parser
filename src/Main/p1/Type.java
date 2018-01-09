package p1;

public enum Type
{
        Dzial, Rozdzial, Artykul, Punkt, Podpunkt, Litera;

        public Type next()
        {
                return Type.values()[(this.ordinal()+1)%Type.values().length];
        }

        public Type prev()
        {
                if(this == Dzial) return this;
                return Type.values()[(this.ordinal()-1)%Type.values().length];
        }

        @Override
        public String toString()
        {
                switch (this)
                {
                        case Dzial:
                                return "DZIAL";
                        case Rozdzial:
                                return "ROZDZIAL";
                        case Artykul:
                                return "ARTYKUL";
                        case Punkt:
                                return "PUNKT";
                        case Podpunkt:
                                return "PODPUNKT";
                        case Litera:
                                return "LITERA";
                }
                return null;
        }
}
