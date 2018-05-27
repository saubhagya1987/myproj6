package vn.com.unit.validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

/**
* An implementation of the Hibernate Validator interface, used to
* determine the uniqueness of a value. <p>
*
* This validator will get the named parameters from the HQL statement and
* retrieve the values for those parameters from the instance of the class
* being validated.
*/
public class UniqueValidator extends SessionAwareConstraintValidator<Object> implements ConstraintValidator<Unique,Object> {
    /** Hibernate session factory for building queries. */
   
    /** Query string to determine a value's uniqueness. */
    private String hql;
    private String idField;
    private String errorAtField;
    private String message;
    /** The collection of named parameters in the HQL statement. */
    private String[] pareprocurement;
    /**
     * Initializes the validator instance with properties from the
     * specified annotation parameters.
     *
     * @param parameters    the parameters of the field to validate's Unique annotation
     */
    public void initialize(final Unique parameters) {
        this.hql = parameters.hql();
        this.pareprocurement = createParameterList(this.hql);
        idField=parameters.idField();
        this.message =  parameters.message();
        this.errorAtField =  parameters.errorAtField();
    }
   
    /**
     * Extracts the named parameters from the specified HQL statement.
     *
     * @param query   the HQL statement to parse
     * @return  an array of all the named parameters (of the form :name) found in the provided string
     */
    private String[] createParameterList(final String query) {
        final Matcher matcher = Pattern.compile(":[^\\s]*").matcher(query);
        List<String> paramList = new ArrayList<String>();
        while(matcher.find()) {
            paramList.add(this.hql.substring(matcher.start() + 1, matcher.end()));
        }
       
        return paramList.toArray(new String[paramList.size()]);
    }

    /**
     * Method to determine whether or not the value passes validation. <p>
     *
     * Validation in this case refers to a value being unique.
     *
     * @param value the value to validate for uniqueness
     * @return  true if the value is unique, false otherwise
     */
   /* public boolean isValid(final Object value) {
        Query query = sessionFactory.getCurrentSession().createQuery(this.hql);
       
        Class valueClass = value.getClass();
        Field field;
        for(int i = 0; i < this.pareprocurement.length; i++) {
            try {
                field = valueClass.getDeclaredField(this.pareprocurement[i]);
                field.setAccessible(true);
               
                query.setParameter(this.pareprocurement[i], (null != field.get(value)) ? field.get(value) : "");
            } catch(final NoSuchFieldException e) {
                throw new SystemException(e.getMessage());
            } catch(final IllegalAccessException e) {
                throw new SystemException(e.getMessage());
            }
        }
       
        return query.list().size() == 0;
    }*/

    /**
     * Sets the Hibernate SessionFactory to use for building queries for validation.
     *
     * @param sessionFactory    a valid SessionFactory instance
     */
    
	private int countRows(Object value) {  
		Class valueClass = value.getClass();
        Field field; 
     
		Query query = getTmpSession().createQuery(this.hql);
	    
        
        for(int i = 0; i < this.pareprocurement.length; i++) {
            try {
                field = valueClass.getDeclaredField(this.pareprocurement[i]);
                field.setAccessible(true);
                
                //QUAN : 24/10/14: check if value field is null => return 0 : always true
                if(!this.pareprocurement[i].equals(idField))
                	if(isNull(field.get(value)))
                		return 0;
                
                // end		
                
                if(field.isAnnotationPresent(javax.validation.constraints.NotNull.class) && field.get(value)==null)
                	return 0;                
                if(this.pareprocurement[i].equals(idField)&&field.get(value)==null)
                	query.setParameter(idField,Long.valueOf(-1) );
                else if(field.getType().equals(String.class)){
                	query.setParameter(this.pareprocurement[i],String.valueOf(field.get(value)).trim());
                }else
                	query.setParameter(this.pareprocurement[i],field.get(value));
            } catch(final NoSuchFieldException e) {
            	return 0;  
               // throw new SystemException(e.getMessage());
            } catch(final IllegalAccessException e) {
              //  throw new SystemException(e.getMessage());
            }
        }
       List<Object> result= query.list();
       return result.size();
    }
	
	// QUAN : 24/10/2014
	// check object is null
	private boolean isNull(Object obj){
		if(obj==null){
			return  false;
		}
		for (Field f : obj.getClass().getDeclaredFields()) {
			  f.setAccessible(true);
			  try {
				if (f.get(obj) != null) {
				     return false;
				  }
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return true;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean isValidInSession(Object value,
			ConstraintValidatorContext context) {
		boolean toReturn = true;
		if ( value == null ) {  
            return toReturn;  
        }  
		toReturn = (countRows( value ) == 0);
		if (!toReturn && StringUtils.isNotEmpty(errorAtField)) {
			context.disableDefaultConstraintViolation();
	        //In the initialiaze method you get the errorMessage: constraintAnnotation.message();
	    	context.buildConstraintViolationWithTemplate(message).addNode(errorAtField).addConstraintViolation();
		}
        return toReturn;  
	}  
}
